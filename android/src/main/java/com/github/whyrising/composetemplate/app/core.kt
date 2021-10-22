package com.github.whyrising.composetemplate.app

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
import androidx.navigation.navArgument
import com.github.whyrising.composetemplate.app.about.composables.AboutPage
import com.github.whyrising.composetemplate.app.common.composables.MyApp
import com.github.whyrising.composetemplate.app.home.composables.HomePage
import com.github.whyrising.composetemplate.app.home.db.AppDbSchema
import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regFx
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.y.collections.core.l
import com.github.whyrising.y.collections.core.v
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

enum class Page {
    HOME,
    ABOUT;

    override fun toString(): String = name

    companion object {
        fun toRoute(route: String?): Page =
            when (route?.substringBefore("/")) {
                HOME.name -> HOME
                ABOUT.name -> ABOUT
                null -> HOME
                else -> throw IllegalArgumentException(
                    "Route $route is not recognized."
                )
            }
    }
}

fun init() {
    regEventDb<Any>(":initialize") { _, _ ->
        AppDbSchema()
    }

    dispatchSync(v(":initialize"))
}

fun register() {
    regSub<AppDbSchema, Page>(":current-page") { db, _ ->
        db.activePage
    }

    regSub<AppDbSchema, String>(":page-title") { db, _ ->
        db.topBarTitle
    }

    regSub<AppDbSchema, String>(":active-page") { db, _ ->
        db.activePage.name
    }

    regSub<AppDbSchema, String>(":home-route") { db, _ ->
        db.homeRoute
    }

    regSub<AppDbSchema, String>(":about-route") { db, _ ->
        db.aboutRoute
    }

    regSub(
        queryId = ":uppercase-title",
        signalsFn = { subscribe<String>(v(":page-title")) }) { title, _ ->
        title.uppercase()
    }

    regFx(":print!") { value ->
        Log.e(":print!", value.toString())
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        register()

        setContent {
            val navController = rememberAnimatedNavController()

            LaunchedEffect(navController) {
                regFx(":navigate!") { route ->
                    navController.navigate("$route")
                }
            }
            MyApp(topBarTitle = subscribe<String>(v(":uppercase-title")).w()) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = subscribe<String>(v(":home-route")).w()
                ) {
                    val offSetX = 300
                    composable(
                        route = subscribe<String>(v(":home-route")).deref(),
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
                        route = subscribe<String>(v(":about-route")).deref(),
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
                        AboutPage(entry.arguments?.getInt("api-version") ?: -1)
                    }
                }
            }
        }
    }
}
