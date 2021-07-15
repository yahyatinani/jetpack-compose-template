package com.why.template.compose.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.google.common.eventbus.Subscribe
import com.why.template.compose.event.dispatch
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.materialisedview.pageViewModel
import com.why.template.compose.presentation.AboutPageEvent
import com.why.template.compose.presentation.HomePageEvent
import com.why.template.compose.presentation.NavigateEvent
import com.why.template.compose.presentation.Route
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

data class Framework(val navHostController: NavHostController) {
    init {
        regFx(":db") { value ->
            if (viewModel == (value as MainViewModel)) return@regFx

            Log.i("updateViewModel", "$value")
            viewModel = value
        }
    }

    @Subscribe
    fun dispatch(vec: ArrayList<Any>) {
        if (vec.isEmpty()) return

        val id = vec[0]
        val eventHandler: Any? = dbEvent[id]

        if (eventHandler != null) {
            val function =
                eventHandler as (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel

            val newVm = function(viewModel.copy(), vec)

            val fxHandler: Any? = fxHandlers[":db"]
            val fx = fxHandler as (value: Any) -> Unit
            fx(newVm)
        } else {
            Log.e("all", "Event handler id not found")
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

//    @Subscribe
//    fun updateViewModel(vm: MainViewModel) {
//        if (viewModel == vm) return
//
//        Log.i("updateViewModel", "$vm")
//        viewModel = vm
//    }

    @Subscribe
    fun homePageEvent(event: HomePageEvent) {
        val vm = pageViewModel(viewModel, event.title, Route.HOME)
        dispatch(vm)
    }

    @Subscribe
    fun aboutPageEvent(event: AboutPageEvent) {
        val vm = pageViewModel(viewModel, event.title, Route.ABOUT)
        dispatch(vm)
    }
}
