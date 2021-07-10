package com.why.template.compose.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.why.template.compose.db.appDb
import com.why.template.compose.event.dispatch
import com.why.template.compose.event.event
import com.why.template.compose.fx.updateCurrentRoute
import com.why.template.compose.presentation.NavigateToEvent
import com.why.template.compose.presentation.Route
import com.why.template.compose.presentation.UpdateCurrentRouteEvent
import com.why.template.compose.presentation.currentRoute
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.home.HomePage

@Composable
private fun OnNavigateEventHandler() {
    val navigatedEvent = event<UpdateCurrentRouteEvent>()

    DisposableEffect(navigatedEvent) {
        val disposable = navigatedEvent.subscribe {
            val prevRoute = appDb.currentRoute
            val currentRoute = it.route
            println("OnNavigate: $prevRoute to ${currentRoute.name}")

            // TODO: the app db needs to be injected somehow?
            updateCurrentRoute(currentRoute)
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

    DisposableEffect(navigationEvent) {
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

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = currentRoute()
    ) {
        composable(Route.HOME.name) {
            HomePage()
        }
        composable(Route.ABOUT.name) {
            AboutPage()
        }
    }

    OnNavigateEventHandler()

    NavigateToEventHandler(navController)

    NavigationListener(navController)
}
