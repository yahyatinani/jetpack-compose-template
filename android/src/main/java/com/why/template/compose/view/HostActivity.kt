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
    val (_, topBarTitle, currentPage) = args

    return vm.copy(
        topBarTitle = topBarTitle as String,
        currentPage = currentPage as Route
    )
}

@Suppress("UnstableApiUsage")
class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regEventDb(":pageViewModelEvent", ::pageViewModel)
        val fxHandler = Framework()

        setContent {
            DisposableEffect(fxHandler) {
                eventBus.register(fxHandler)

                onDispose {
                    Log.i("onDispose", "Framework")
                    eventBus.unregister(fxHandler)
                }
            }

            val navController = rememberNavController()

            regFx(":navigate!") { value ->
                navController.navigate(value as String)
            }

            regEventFx(":navigate") { _, vec ->
                val route = vec[1]
                mapOf(
                    ":fx" to arrayListOf(
                        arrayListOf(":navigate!", route)
                    )
                )
            }

            regSub(":pageTitle") { vm, _ ->
                vm.topBarTitle
            }

            regSub(":vm") { vm, _ ->
                vm
            }

            MyApp(title = subscribe(arrayListOf(":pageTitle"))) {
                NavHost(
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    composable(Route.HOME.name) {
                        HomePage()
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
                            apiVersion = entry.arguments?.getInt("api-v") ?: -1
                        )
                    }
                }
            }
        }
    }
}
