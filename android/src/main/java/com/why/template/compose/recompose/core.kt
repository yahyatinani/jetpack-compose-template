package com.why.template.compose.recompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.why.template.compose.recompose.Context.*
import com.why.template.compose.recompose.Interceptor.*
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

enum class Interceptor {
    Id,
    Before,
    After
}

enum class Context {
    Effects,
    Coeffects,
    Db,
    Event
}

fun regEventFx(
    id: Any,
    handler: (cofx: Map<Any, Any>, vec: ArrayList<Any>) -> Map<Any, Any>
) {
    Log.i("regEventFx", "$id")
    if (fxEvent[id] != null)
        Log.w("regEventFx: ", "overwriting handler for: $id")

    fxEvent[id] = handler

}

/***
 * Associate the given event `id` with the given collection of `interceptors`.
 */
fun register(id: Any, interceptors: ArrayList<Any>) {
    TODO()
}

fun toInterceptor(
    id: Any,
    before: (context: Map<Any, Any>) -> Any = {},
    after: (context: Map<Any, Any>) -> Any = {}
): Map<Interceptor, Any> = mapOf(
    Id to id,
    Before to before,
    After to after,
)

/**
 * Context:
 *
 * {:coeffects {:event [:some-id :some-param]
 *              :db    <original contents of app-db>}
 *
 *  :effects   {:db        <new value for app-db>
 *              :dispatch  [:an-event-id :param1]}
 *
 *  :queue     [a collection of further interceptors]
 *
 *  :stack     [a collection of interceptors already walked]}
 */
fun dbHandlerToInterceptor(
    handlerFn: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
): Map<Interceptor, Any> = toInterceptor(
    id = ":db-handler",
    before = { context: Map<Any, Any> ->
        val cofx = context[Coeffects] as Map<*, *>
        val db = cofx[Db] as MainViewModel
        val event = cofx[Event] as ArrayList<Any>

        val newDb = handlerFn(db, event)

        val newContext = cofx.plus(Db to newDb).let { newCofx ->
            context.plus(Coeffects to newCofx)
        }

        newContext
    }
)

val doFx: Map<Interceptor, Any> = toInterceptor(
    id = ":do-fx",
    after = { context: Map<Any, Any> ->
        val effects = context[Effects] as Map<*, *>
        val effectsWithoutDb = effects.minus(Db)

        val newDb = effects[Db] as MainViewModel?
        if (newDb != null) {
            val fxFn = fxHandlers[":db"] as (value: Any) -> Unit
            fxFn(newDb)
        }

        for ((effectKey, effectValue) in effectsWithoutDb) {
            val fxFn = fxHandlers[effectKey] as ((value: Any) -> Unit)?

            if (fxFn != null && effectValue != null)
                fxFn(effectValue)
            else
                Log.i(
                    "re-compose",
                    "no handler registered for effect: $effectKey. Ignoring."
                )
        }
    }
)

/**
 * Register the given event `handler` (function) for the given `id`.
 */
fun regEventDb(
    id: Any,
    interceptors: ArrayList<Any>,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
//    Log.i("regEventDb", "$id")
//
//    if (dbEvent[id] != null)
//        Log.w("regEventDb: ", "overwriting handler for: $id")
//    dbEvent[id] = handler

    register(
        id,
        arrayListOf(doFx, interceptors, dbHandlerToInterceptor(handler))
    )
}

fun regEventDb(
    id: Any,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
    regEventDb(id, arrayListOf(), handler)
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
