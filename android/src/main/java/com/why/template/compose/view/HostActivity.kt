package com.why.template.compose.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.why.template.compose.presentation.MainViewModel
import com.why.template.compose.presentation.Route
import com.why.template.compose.recompose.*
import com.why.template.compose.recompose.events.event
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

fun pageInfoHandler(
    db: MainViewModel?,
    vec: ArrayList<Any>
): MainViewModel {
    val (_, topBarTitle, currentPage) = vec

    return db!!.copy(
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

        regEventDb<MainViewModel>(":initialize") { _, _ ->
            MainViewModel()
        }

        dispatchSync(event(":initialize"))

        regEventDb(":pageInfoEvent", ::pageInfoHandler)

        regEventDb<MainViewModel>(":inc") { db, _ ->
            db!!.copy(counter = db.counter + 1)
        }

        regEventFx(":navigate") { _, vec ->
            val route = vec[1]
            mapOf(
                ":fx" to arrayListOf(
                    event(":navigate!", route)
                )
            )
        }

        regSub<MainViewModel>(":get-title") { db, _ ->
            Log.i(":get-title", "from $db")
            db.topBarTitle
        }

        regSub(
            queryId = ":uppercase-title",
            inputFn = {
                subscribe(event(":get-title"))
            }) { title, _ ->
            val uppercase = (title as String).uppercase()
            Log.i(":uppercase-title", uppercase)
            uppercase
        }

        setContent {
            val navController = rememberNavController()

            LaunchedEffect(navController) {
                regFx(":navigate!") { value ->
                    navController.navigate(value as String)
                }
            }

            MyApp(topBarTitle = subscribe(event(":uppercase-title"))) {
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
