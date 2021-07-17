package com.why.template.compose.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.eventbus.Subscribe
import com.why.template.compose.materialisedview.MainViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

private var viewModel by mutableStateOf(MainViewModel())

private fun mainViewModel() = viewModel.copy()

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

fun <T> subscribe(qvec: ArrayList<Any>): T {
    val id = qvec[0]

    return when (val r = queryFns[id]) {
        null -> throw IllegalArgumentException(
            "No query function was found for the given id: `$id`"
        )
        is Array<*> -> {
            val inputFn = r[0] as (ArrayList<Any>) -> Any
            val computationFn = r[1] as (Any, ArrayList<Any>) -> Any

            // TODO: Implement input with [v1 v2] return
            val input = inputFn(qvec)
            val cache = memSubComp[input]

            if (cache == null) {
                val computation = computationFn(input, qvec)
                memSubComp[input] = computation
                computation as T
            } else {
                cache as T
            }
        }
        else -> {
            val function = r as (MainViewModel, ArrayList<Any>) -> Any
            function(mainViewModel(), qvec) as T
        }
    }
}

@Suppress("UnstableApiUsage")
class Framework : ViewModel() {
    init {
        regFx(":db") { value ->
            if (viewModel == (value as MainViewModel)) return@regFx

            Log.i("updateViewModel", "$value")
            viewModel = value
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("onCleared", "onCleared")
    }

    @Subscribe
    fun receive(vec: ArrayList<Any>) {
        val channel = Channel<Int>()

        if (vec.isEmpty()) return
        val eventId = vec[0]

        viewModelScope.launch {
            // this might be heavy CPU-consuming computation or async logic,
            // we'll just send five squares for (x in 1..5) channel.send(x * x)
            val eventDb: Any? = dbEvent[eventId]

            if (eventDb != null) {
                val function = eventDb
                        as (MainViewModel, ArrayList<Any>) -> MainViewModel

                val newVm = function(viewModel.copy(), vec)

                val fxHandler: Any? = fxHandlers[":db"]
                val fx = fxHandler as (value: Any) -> Unit
                fx(newVm)

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
}
