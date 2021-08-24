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
import com.github.whyrising.recompose.Keys
import com.github.whyrising.recompose.events.event
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.recompose.regFx
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.y.collections.core.get
import com.github.whyrising.y.collections.core.m
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.why.template.compose.presentation.MainViewModel
import com.why.template.compose.presentation.Route
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

        regEventDb(":homePage") { db, vec ->
            val (_, topBarTitle, currentPage) = vec

            (db as MainViewModel).copy(
                topBarTitle = topBarTitle as String,
                currentPage = currentPage as Route,
                navigateButtonFlag = true
            )
        }

        regEventDb(":aboutPage") { db, vec ->
            val (_, topBarTitle, currentPage) = vec

            (db as MainViewModel).copy(
                topBarTitle = topBarTitle as String,
                currentPage = currentPage as Route
            )
        }

        regEventDb(":inc") { db, _ ->
            (db as MainViewModel).copy(counter = db.counter + 1)
        }

        regEventFx(":navigate") { cofx, vec ->
            val viewModel = get(cofx, Keys.db) as MainViewModel
            val route = vec[1]
            m(
                Keys.db to viewModel.copy(navigateButtonFlag = false),
                Keys.fx to arrayListOf(
                    event(":navigate!", route),
                    event(":print!", "I'm currently in About page. yeeeeeah"),
                    event(Keys.dispatch, event(":inc"))
                )
            )
        }

        regSub<MainViewModel>(":nav-button-flag") { db, _ ->
            db.navigateButtonFlag
        }

        regSub<MainViewModel>(":get-title") { db, _ ->
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
