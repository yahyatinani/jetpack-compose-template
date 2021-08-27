package com.why.template.compose.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.github.whyrising.recompose.Framework
import com.github.whyrising.recompose.events.event
import com.github.whyrising.recompose.regFx
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.why.template.compose.data.Spec
import com.why.template.compose.data.Route
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

class HostActivity : ComponentActivity() {
    private val fxHandler = Framework()

    override fun onDestroy() {
        super.onDestroy()

        Log.i("onDispose", "Framework")
        fxHandler.halt()
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regSub<Spec>(":get-title") { db, _ ->
            Log.i(":get-title", "from $db")
            db.topBarTitle
        }

        regSub(
            queryId = ":uppercase-title",
            inputFn = {
                subscribe(event(":get-title"))
            }
        ) { title, _ ->
            val uppercase = (title as String).uppercase()
            Log.i(":uppercase-title", uppercase)
            uppercase
        }

        setContent {
            val navController = rememberAnimatedNavController()

            LaunchedEffect(navController) {
                regFx(":navigate!") { value ->
                    navController.navigate(value as String)
                }

                regFx(":print!") { value ->
                    Log.e(":print!", value.toString())
                }
            }

            MyApp(topBarTitle = subscribe(event(":uppercase-title"))) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    val offSetX = 300
                    val duration = 300
                    composable(
                        route = Route.HOME.name,
                        exitTransition = { _, _ ->
                            slideOutHorizontally(
                                targetOffsetX = { -offSetX },
                                animationSpec = tween(duration)
                            ) + fadeOut(animationSpec = tween(duration))
                        },
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -offSetX },
                                animationSpec = tween(duration)
                            ) + fadeIn(animationSpec = tween(duration))
                        },
                    ) {
                        HomePage()
                    }

                    composable(
                        route = "${Route.ABOUT.name}/{api-v}",
                        arguments = listOf(
                            navArgument("api-v") {
                                type = NavType.IntType
                            }
                        ),
                        enterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { offSetX },
                                animationSpec = tween(duration)
                            ) + fadeIn(animationSpec = tween(duration))
                        },
                        popExitTransition = { _, _ ->
                            slideOutHorizontally(
                                targetOffsetX = { offSetX },
                                animationSpec = tween(durationMillis = duration)
                            ) + fadeOut(animationSpec = tween(duration))
                        }
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
