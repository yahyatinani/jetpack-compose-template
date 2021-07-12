package com.why.template.compose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.why.template.compose.event.dispatch
import com.why.template.compose.event.event
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.materialisedview.updateCurrentRoute
import com.why.template.compose.presentation.NavigateToEvent
import com.why.template.compose.presentation.Route
import com.why.template.compose.presentation.UpdateCurrentRouteEvent
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

@Composable
private fun UpdateCurrentRouteEventHandler(viewModel: MainViewModel) {
    val updateCurrentRouteEvent = event<UpdateCurrentRouteEvent>()

    DisposableEffect(updateCurrentRouteEvent) {
        val disposable = updateCurrentRouteEvent.subscribe {
            val currentRoute = it.route
            println("OnNavigate: to ${currentRoute.name}")

            dispatch(updateCurrentRoute(viewModel, currentRoute))
        }

        onDispose {
            println("onDispose: unsubscribe from NavigatedEvent")
            disposable.dispose()
        }
    }
}

@Composable
private fun NavigateToEventHandler(navController: NavHostController) {
    val navigationEvent = event<NavigateToEvent>()

    DisposableEffect(navController) {
        val disposable = navigationEvent.subscribe {
            println("NavigateToEvent: ${it.route.name}")
            navController.navigate(it.route.name)
        }

        onDispose {
            println("onDispose: unsubscribe from NavigateToEvent")
            disposable.dispose()
        }
    }
}

@Composable
private fun NavigationListener(navController: NavHostController) {
    val listener = OnDestinationChangedListener { _, destination, _ ->
        println("Dispatch UpdateCurrentRouteEvent: ${destination.route}")

        dispatch(UpdateCurrentRouteEvent(Route.toRoute(destination.route)))
    }

    DisposableEffect(navController) {
        println("<< ADD navigation Listener >>")
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            println("<< REMOVE navigation Listener >>")

            navController.removeOnDestinationChangedListener(listener)
        }
    }
}

class HostActivity : ComponentActivity() {
    private var viewModel by mutableStateOf(MainViewModel())

    private fun update(vm: MainViewModel) {
        viewModel = vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val updateViewEvent = event<MainViewModel>()

            DisposableEffect(updateViewEvent) {
                val disposable = updateViewEvent.subscribe {
                    update(it)
                }

                onDispose {
                    disposable.dispose()
                }
            }

            MyApp(viewModel) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = viewModel.currentPage.name
                ) {
                    composable(Route.HOME.name) { HomePage(viewModel) }
                    composable(Route.ABOUT.name) { AboutPage(viewModel) }
                }

                UpdateCurrentRouteEventHandler(viewModel)
                NavigateToEventHandler(navController)
                NavigationListener(navController)
            }
        }
    }
}
