package com.why.template.compose.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.why.template.compose.event.eventBus
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.presentation.Route
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

fun pageViewModel(vm: MainViewModel, args: ArrayList<Any>): MainViewModel {
    val topBarTitle = args[1] as String
    val currentPage = args[2] as Route

    return vm.copy(topBarTitle = topBarTitle, currentPage = currentPage)
}

class HostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regEventDb(":pageViewModelEvent", ::pageViewModel)

        setContent {
            val navController = rememberNavController()
            DisposableEffect(navController) {
                val fxHandler = Framework(navController)
                eventBus.register(fxHandler)

                onDispose {
                    Log.i("go-to ->", "<< REMOVE navigation Listener >>")
                    eventBus.unregister(fxHandler)
                }
            }

            MyApp(viewModel) {
                NavHost(
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    composable(Route.HOME.name) {
                        HomePage(viewModel)
                    }

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
