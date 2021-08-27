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
import com.why.template.compose.data.Route
import com.why.template.compose.data.Spec
import com.why.template.compose.view.about.AboutPage
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

private fun register() {
    // subscriptions
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

    // effects
    regFx(":print!") { value ->
        Log.e(":print!", value.toString())
    }
}

@ExperimentalAnimationApi
private fun exitTransition(
    targetOffsetX: Int,
    duration: Int = 300
) = slideOutHorizontally(
    targetOffsetX = { targetOffsetX },
    animationSpec = tween(duration)
) + fadeOut(animationSpec = tween(duration))

@ExperimentalAnimationApi
private fun enterTransition(
    initialOffsetX: Int,
    duration: Int = 300
) = slideInHorizontally(
    initialOffsetX = { initialOffsetX },
    animationSpec = tween(duration)
) + fadeIn(animationSpec = tween(duration))

class HostActivity : ComponentActivity() {
    private val recompose = Framework()

    override fun onDestroy() {
        super.onDestroy()

        Log.i("onDispose", "Framework")
        recompose.halt()
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        register()

        setContent {
            val navController = rememberAnimatedNavController()

            LaunchedEffect(navController) {
                regFx(":navigate!") { value ->
                    navController.navigate(value as String)
                }
            }

            MyApp(topBarTitle = subscribe(event(":uppercase-title"))) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    val offSetX = 300
                    composable(
                        route = Route.HOME.name,
                        exitTransition = { _, _ ->
                            exitTransition(targetOffsetX = -offSetX)
                        },
                        popEnterTransition = { _, _ ->
                            enterTransition(initialOffsetX = -offSetX)
                        },
                    ) {
                        HomePage()
                    }

                    composable(
                        route = "${Route.ABOUT.name}/{api-v}",
                        arguments = listOf(
                            navArgument("api-v") { type = NavType.IntType }
                        ),
                        enterTransition = { _, _ ->
                            enterTransition(initialOffsetX = offSetX)
                        },
                        popExitTransition = { _, _ ->
                            exitTransition(targetOffsetX = offSetX)
                        }
                    ) { entry ->
                        AboutPage(entry.arguments?.getInt("api-v") ?: -1)
                    }
                }
            }
        }
    }
}
