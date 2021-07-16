package com.why.template.compose.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.common.eventbus.Subscribe
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.presentation.NavigateEvent
import com.why.template.compose.presentation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

var viewModel by mutableStateOf(MainViewModel())
    private set

val dbEvent = ConcurrentHashMap<Any, Any>()
val fxEvent = ConcurrentHashMap<Any, Any>()
val fxHandlers = ConcurrentHashMap<Any, Any>()

fun regEventFx(
    id: Any,
    handler: (cofx: Map<Any, Any>, vec: ArrayList<Any>) -> Map<Any, Any>
) {
    fxEvent[id] = handler
}

fun regEventDb(
    id: Any,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
    dbEvent[id] = handler
}

fun regFx(id: Any, handler: (value: Any) -> Unit) {
    fxHandlers[id] = handler
}

@Suppress("UnstableApiUsage")
data class Framework(val navHostController: NavHostController) : ViewModel() {
    init {
        regFx(":db") { value ->
            if (viewModel == (value as MainViewModel)) return@regFx

            Log.i("updateViewModel", "$value")
            viewModel = value
        }
    }

    @Subscribe
    fun dispatch(vec: ArrayList<Any>) {
        val channel = Channel<Int>()

        if (vec.isEmpty()) return
        val eventId = vec[0]

        viewModelScope.launch {
            // this might be heavy CPU-consuming computation or async logic,
            // we'll just send five squares for (x in 1..5) channel.send(x * x)
            val eventDb: Any? = dbEvent[eventId]

            if (eventDb != null) {
                val function =
                    eventDb as (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel

                val newVm = function(viewModel.copy(), vec)

                val fxHandler: Any? = fxHandlers[":db"]
                val fx = fxHandler as (value: Any) -> Unit
                fx(newVm)
            } else {
                Log.e("all", "Event handler id not found")
            }
        }
    }

    @Subscribe
    fun navigateTo(route: Route) {
        Log.i("go-to ->", route.name)
        navHostController.navigate(route.name)
    }

    @Subscribe
    fun navigateTo(event: NavigateEvent) {
        Log.i("go-to ->", event.route)
        navHostController.navigate(event.route)
    }
}