package com.why.template.compose.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.common.eventbus.Subscribe
import com.why.template.compose.event.eventBus
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.presentation.NavigateToEvent
import com.why.template.compose.presentation.Route
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

var viewModel by mutableStateOf(MainViewModel())

data class EventsHandler(val navHostController: NavHostController) {
    @Subscribe
    fun navigateTo(route: Route) {
        Log.i("go-to ->", route.name)
        navHostController.navigate(route.name)
    }

    @Subscribe
    fun navigateTo(event: NavigateToEvent) {
        Log.i("go-to ->", event.route)
        navHostController.navigate(event.route)
    }

    @Subscribe
    fun updateViewModel(vm: MainViewModel) {
        Log.i("updateViewModel", "$vm")
        viewModel = vm
    }
}

class HostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            DisposableEffect(navController) {
                val eventHandler = EventsHandler(navController)
                eventBus.register(eventHandler)

                onDispose {
                    Log.i("go-to ->", "<< REMOVE navigation Listener >>")
                    eventBus.unregister(eventHandler)
                }
            }

            MyApp(viewModel) {
                NavHost(
                    navController = navController,
                    startDestination = viewModel.currentPage.name
                ) {
                    composable(Route.HOME.name) { HomePage(viewModel) }
                    composable(
                        route = "${Route.ABOUT.name}/{api-v}",
                        arguments = listOf(
                            navArgument("api-v") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->
                        AboutPage(
                            viewModel = viewModel,
                            apiVersion = entry.arguments?.getInt("api-v") ?: -1
                        )
                    }
                }
            }
        }
    }
}
