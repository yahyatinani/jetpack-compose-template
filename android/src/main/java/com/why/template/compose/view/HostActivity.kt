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
import com.why.template.compose.presentation.Route
import com.why.template.compose.recompose.*
import com.why.template.compose.recompose.db.MainViewModel
import com.why.template.compose.recompose.subs.subscribe
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

class HostActivity : ComponentActivity() {
    private val fxHandler = Framework()

    override fun onDestroy() {
        super.onDestroy()

        Log.i("onDispose", "Framework")
        fxHandler.halt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regEventDb(":pageViewModelEvent", ::pageViewModel)

        regEventDb(":inc") { vm, _ ->
            vm.copy(counter = vm.counter + 1)
        }

        regEventFx(":navigate") { _, vec ->
            val route = vec[1]
            mapOf(
                ":fx" to arrayListOf(
                    arrayListOf(":navigate!", route)
                )
            )
        }

        regSub(":get-title") { vm, _ ->
            Log.i(":get-title", "from $vm")
            vm.topBarTitle
        }

        regSub(
            queryId = ":uppercase-title",
            inputFn = {
                subscribe(arrayListOf(":get-title"))
            }) { title, _ ->
            val uppercase = (title as String).uppercase()
            Log.i(":uppercase-title", uppercase)
            uppercase
        }

        setContent {
            val navController = rememberNavController()

            DisposableEffect(navController) {
                regFx(":navigate!") { value ->
                    navController.navigate(value as String)
                }

                onDispose {

                }
            }

            MyApp(topBarTitle = subscribe(arrayListOf(":uppercase-title"))) {
                NavHost(
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    composable(Route.HOME.name) {
                        Log.i("NavHost", "HomePage")
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
