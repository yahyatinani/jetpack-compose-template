package com.github.whyrising.composetemplate.app

import android.os.Bundle
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
import androidx.navigation.navArgument
import com.github.whyrising.composetemplate.app.Screens.ABOUT
import com.github.whyrising.composetemplate.app.Screens.HOME
import com.github.whyrising.composetemplate.app.about.composables.AboutScreen
import com.github.whyrising.composetemplate.app.common.composables.MyApp
import com.github.whyrising.composetemplate.app.home.composables.HomeScreen
import com.github.whyrising.composetemplate.app.home.db.DbSchema
import com.github.whyrising.composetemplate.app.home.events.initDbHandler
import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regFx
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.y.collections.core.get
import com.github.whyrising.y.collections.core.l
import com.github.whyrising.y.collections.core.m
import com.github.whyrising.y.collections.core.v
import com.github.whyrising.y.collections.map.IPersistentMap
import com.github.whyrising.y.collections.vector.IPersistentVector
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

enum class Screens {
    HOME,
    ABOUT
}

val routes: IPersistentMap<Screens, String> = m(
    HOME to "home",
    ABOUT to "about/{api-version}"
)

fun route(screen: Screens): String = routes[screen]!!

fun register() {
    regSub<DbSchema, String>(":screen-title") { db, _ ->
        db.topBarTitle
    }

    regSub(
        queryId = ":uppercase-title",
        signalsFn = { subscribe<String>(v(":screen-title")) }
    ) { title, _ ->
        title.uppercase()
    }
}

@ExperimentalAnimationApi
fun exitTransition(
    targetOffsetX: Int,
    duration: Int = 300
) = slideOutHorizontally(
    targetOffsetX = { targetOffsetX },
    animationSpec = tween(duration)
) + fadeOut(animationSpec = tween(duration))

@ExperimentalAnimationApi
fun enterTransition(
    initialOffsetX: Int,
    duration: Int = 300
) = slideInHorizontally(
    initialOffsetX = { initialOffsetX },
    animationSpec = tween(duration)
) + fadeIn(animationSpec = tween(duration))

@ExperimentalAnimationApi
class HostActivity : ComponentActivity() {
    init {
        regEventDb(":initialize", handler = ::initDbHandler)
        dispatchSync(v(":initialize"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        register()

        setContent {
            val navController = rememberAnimatedNavController()

            LaunchedEffect(navController) {
                regFx(":navigate!") { route ->
                    if (route == null)
                        return@regFx

                    val (screen) = route as IPersistentVector<*>
                    when (screen) {
                        HOME -> TODO()
                        ABOUT -> {
                            val arg = route[1]
                            navController.navigate("$screen/$arg")
                        }
                    }
                }
            }

            MyApp(topBarTitle = subscribe<String>(v(":uppercase-title")).w()) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = route(HOME)
                ) {
                    val offSetX = 300
                    composable(
                        route = route(HOME),
                        exitTransition = { _, _ ->
                            exitTransition(targetOffsetX = -offSetX)
                        },
                        popEnterTransition = { _, _ ->
                            enterTransition(initialOffsetX = -offSetX)
                        },
                    ) {
                        HomeScreen()
                    }

                    composable(
                        route = route(ABOUT),
                        arguments = l(
                            navArgument("api-version") {
                                type = NavType.IntType
                            }
                        ),
                        enterTransition = { _, _ ->
                            enterTransition(initialOffsetX = offSetX)
                        },
                        popExitTransition = { _, _ ->
                            exitTransition(targetOffsetX = offSetX)
                        }
                    ) { entry ->
                        AboutScreen(
                            entry.arguments?.getInt("api-version") ?: -1
                        )
                    }
                }
            }
        }
    }
}
