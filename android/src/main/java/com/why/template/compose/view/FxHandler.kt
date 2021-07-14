package com.why.template.compose.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.github.whyrising.y.concretions.vector.PersistentVector
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
val fxHandlers = ConcurrentHashMap<String, Any>()

fun regEventDb(
    id: Any,
    handler: (vm: MainViewModel, vec: PersistentVector<Any>) -> MainViewModel
) {
    dbEvent[id] = handler
}

data class FxHandler(val navHostController: NavHostController) {
    @Subscribe
    fun all(vec: PersistentVector<Any>) {
        val id = vec[0]
        val handler: Any? = dbEvent[id]

        if (handler != null) {
            val function =
                handler as (vm: MainViewModel, vec: PersistentVector<Any>) -> MainViewModel

            val newVm = function(viewModel.copy(), vec)

            dispatch(newVm)
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

    @Subscribe
    fun updateViewModel(vm: MainViewModel) {
        if (viewModel == vm) return

        Log.i("updateViewModel", "$vm")
        viewModel = vm
    }

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
