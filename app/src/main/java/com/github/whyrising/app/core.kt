package com.github.whyrising.app

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.github.whyrising.app.about.AboutScreen
import com.github.whyrising.app.home.HomeScreen
import com.github.whyrising.app.home.defaultDb
import com.github.whyrising.app.home.regHomeEvents
import com.github.whyrising.app.home.regHomeSubs
import com.github.whyrising.app.ui.animation.nav.enterAnimation
import com.github.whyrising.app.ui.animation.nav.exitAnimation
import com.github.whyrising.app.ui.theme.JetpackComposeTemplateTheme
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regFx
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.y.collections.core.v
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun HostScreen(content: @Composable (padding: PaddingValues) -> Unit = {}) {
    JetpackComposeTemplateTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(subscribe<String>(v(":format-screen-title")).w())
                    },
                    elevation = 1.dp
                )
            },
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

// -- Navigation ---------------------------------------------------------------

object Routes {
    const val home = "/home"
    const val about = "/about"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(animOffSetX: Int) {
    composable(
        route = Routes.home,
        exitTransition = { _, _ ->
            exitAnimation(targetOffsetX = -animOffSetX)
        },
        popEnterTransition = { _, _ ->
            enterAnimation(initialOffsetX = -animOffSetX)
        }
    ) {
        HomeScreen()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.aboutComposable(animOffSetX: Int) {
    composable(
        route = Routes.about,
        enterTransition = { _, _ ->
            enterAnimation(initialOffsetX = animOffSetX)
        },
        popExitTransition = { _, _ ->
            exitAnimation(targetOffsetX = animOffSetX)
        },
    ) {
        AboutScreen()
    }
}

@ExperimentalAnimationApi
@Composable
fun Navigation(padding: PaddingValues) {
    val navController = rememberAnimatedNavController()

    LaunchedEffect(navController) {
        regFx(":navigate!") { route ->
            if (route == null)
                return@regFx

            navController.navigate("$route")
        }
    }
    AnimatedNavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = Routes.home
    ) {
        homeComposable(animOffSetX = 300)
        aboutComposable(animOffSetX = 300)
    }
}

// -- Entry Point --------------------------------------------------------------

fun initAppDb() {
    regEventDb<Any>(":init-db") { _, _ -> defaultDb }
    dispatchSync(v(":init-db"))
}

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppDb()
        regHomeEvents()
        regHomeSubs()

        setContent {
            HostScreen {
                Navigation(padding = it)
            }
        }
    }
}

// -- Previews -----------------------------------------------------------------

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    JetpackComposeTemplateTheme {
        HostScreen()
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ScreenDarkPreview() {
    JetpackComposeTemplateTheme {
        HostScreen()
    }
}
