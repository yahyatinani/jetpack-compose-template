package com.why.template.compose.recompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.why.template.compose.recompose.db.MainViewModel
import com.why.template.compose.recompose.db.appDb
import com.why.template.compose.recompose.db.appState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

val dbEvent = ConcurrentHashMap<Any, Any>()
val fxEvent = ConcurrentHashMap<Any, Any>()

val fxHandlers = ConcurrentHashMap<Any, Any>()
val queryFns = ConcurrentHashMap<Any, Any>()
val memSubComp = ConcurrentHashMap<Any, Any>()

fun regEventFx(
    id: Any,
    handler: (cofx: Map<Any, Any>, vec: ArrayList<Any>) -> Map<Any, Any>
) {
    Log.i("regEventFx", "$id")
    if (fxEvent[id] != null)
        Log.w("regEventFx: ", "overwriting handler for: $id")

    fxEvent[id] = handler

}

fun regEventDb(
    id: Any,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
    Log.i("regEventDb", "$id")

    if (dbEvent[id] != null)
        Log.w("regEventDb: ", "overwriting handler for: $id")
    dbEvent[id] = handler
}

fun regFx(id: Any, handler: (value: Any) -> Unit) {
    Log.i("regFx", "$id")

    if (dbEvent[id] != null)
        Log.w("regFx: ", "overwriting handler for: $id")
    fxHandlers[id] = handler
}

fun regSub(
    queryId: Any,
    computationFn: (vm: MainViewModel, queryVec: ArrayList<Any>) -> Any,
) {
    queryFns[queryId] = computationFn
}

fun regSub(
    queryId: Any,
    inputFn: (queryVec: ArrayList<Any>) -> Any,
    computationFn: (input: Any, queryVec: ArrayList<Any>) -> Any,
) {
    queryFns[queryId] = arrayOf(inputFn, computationFn)
}

class Framework : ViewModel() {
    init {
        println("init: $appDb")
        regFx(":db") { value ->
            if (appState == (value as MainViewModel)) return@regFx

            Log.i("update appState", "$value")
            appState = value
        }
    }

    private val receiver: Disposable =
        subscribe<ArrayList<Any>>().subscribe { vec ->
            if (vec.isEmpty()) return@subscribe

            val eventId = vec[0]

            viewModelScope.launch {
                // this might be heavy CPU-consuming computation or async logic
                val eventDb: Any? = dbEvent[eventId]

                if (eventDb != null) {
                    val function = eventDb
                            as (MainViewModel, ArrayList<Any>) -> MainViewModel

                    val newAppDb = function(appState, vec)

                    val fxHandler: Any? = fxHandlers[":db"]
                    val fx = fxHandler as (value: Any) -> Unit
                    fx(newAppDb)

                    return@launch
                }

                val eventFx: Any? = fxEvent[eventId]

                if (eventFx != null) {
                    val function = eventFx
                            as (Map<Any, Any>, ArrayList<Any>) -> Map<Any, Any>

                    val fxMap = function(mapOf(), vec)
                    val fxVec = fxMap[":fx"] as ArrayList<ArrayList<Any>>
                    fxVec.forEach { effectVec ->
                        val id = effectVec[0]
                        val value = effectVec[1]
                        val fx = fxHandlers[id] as (value: Any) -> Unit
                        fx(value)
                    }

                    return@launch
                } else throw IllegalArgumentException(
                    "Event handler not found for id: $eventId"
                )
            }
        }

    fun halt() {
        Log.i("halt: ", "event receiver")
        receiver.dispose()
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("onCleared", "Resources released")
        halt()
    }

    companion object {

    }
}

val publisher: PublishSubject<Any> = PublishSubject.create()

inline fun <reified T> subscribe(): Observable<T> = publisher.filter {
    it is T
}.map {
    it as T
}

fun dispatch(event: Any) {
    Log.i("dispatch", "$event")
    publisher.onNext(event)
}
