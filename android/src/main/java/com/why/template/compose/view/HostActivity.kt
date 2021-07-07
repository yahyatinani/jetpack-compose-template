package com.why.template.compose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.why.template.compose.presentation.AppDb
import com.why.template.compose.presentation.EventBus
import com.why.template.compose.presentation.NavigatedEvent
import com.why.template.compose.presentation.NavigationEvent
import com.why.template.compose.presentation.Routes
import com.why.template.compose.presentation.updateCurrentRoute
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

@Composable
private fun NavigateEventHandler(navController: NavHostController) {
    val navigationEvent = EventBus.subscribe<NavigationEvent>()

    DisposableEffect(navigationEvent) {
        val disposable = navigationEvent.subscribe {
            println("NavigationEvent to : ${it.route.name}")
            navController.navigate(it.route.name)
        }

        onDispose {
            println("onDispose: unsubscribe from NavigationEvent")
            disposable.dispose()
        }
    }
}

@Composable
private fun NavigatedEventHandler() {
    val navigatedEvent = EventBus.subscribe<NavigatedEvent>()

    DisposableEffect(navigatedEvent) {
        val disposable = navigatedEvent.subscribe {
            println(
                "Update Db.currentRoute: ${AppDb.currentRoute} to ${it.route.name}"
            )

            updateCurrentRoute(it.route)
        }

        onDispose {
            println("onDispose: unsubscribe from NavigatedEvent")
            disposable.dispose()
        }
    }
}

@Composable
private fun OnNavigateDispatch(navController: NavHostController) {
    val listener = OnDestinationChangedListener { _, destination, _ ->
        println("Navigated to : $destination")

        EventBus.dispatch(NavigatedEvent(Routes.toRoute(destination.route)))
    }

    DisposableEffect(navController) {
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDb.currentRoute.name
    ) {
        composable(Routes.HOME.name) {
            HomePage()
        }
        composable(Routes.ABOUT.name) {
            AboutPage()
        }
    }

    NavigateEventHandler(navController)

    NavigatedEventHandler()

    OnNavigateDispatch(navController)
}

class HostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApp {
                Navigation()
            }
        }
    }
}
