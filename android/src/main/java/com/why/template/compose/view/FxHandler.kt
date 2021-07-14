package com.why.template.compose.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.google.common.eventbus.Subscribe
import com.why.template.compose.event.eventBus
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.materialisedview.pageViewModel
import com.why.template.compose.presentation.AboutPageEvent
import com.why.template.compose.presentation.HomePageEvent
import com.why.template.compose.presentation.NavigateEvent
import com.why.template.compose.presentation.Route

var viewModel by mutableStateOf(MainViewModel())
    private set

data class FxHandler(val navHostController: NavHostController) {
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
        eventBus.post(vm)
    }

    @Subscribe
    fun aboutPageEvent(event: AboutPageEvent) {
        val vm = pageViewModel(viewModel, event.title, Route.ABOUT)
        eventBus.post(vm)
    }
}
