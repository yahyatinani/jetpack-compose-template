package com.github.whyrising.composetemplate.app.home.composables

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.whyrising.composetemplate.app.Screens.ABOUT
import com.github.whyrising.composetemplate.app.common.composables.MyApp
import com.github.whyrising.composetemplate.app.home.events.regHomeScreenEvents
import com.github.whyrising.composetemplate.app.home.subs.regHomePageSubs
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.y.collections.core.v
import com.why.template.compose.R

@Composable
fun helloText(name: String): AnnotatedString = buildAnnotatedString {
    append("Hello ")
    withStyle(
        SpanStyle(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant
        )
    ) {
        append(name)
    }
    append(" \uD83D\uDE01")
}

@Composable
fun Greeting(name: String) {
    Text(
        modifier = Modifier.padding(24.dp),
        text = helloText(name)
    )
}

@Composable
fun HomeScreen() {
    val title = stringResource(R.string.top_bar_home_title)
    LaunchedEffect(true) {
        Log.i("LaunchedEffect", "Home Screen")
        dispatch(v(":homePage", title, true))
    }

    val androidVersion = Build.VERSION.SDK_INT
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Android $androidVersion")

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = subscribe<String>(v(":counter")).w())

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { dispatch(v(":inc")) }) {
            Text(text = "Count")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { dispatch(v(":navigate", ABOUT, androidVersion)) },
            enabled = subscribe<Boolean>(v(":is-nav-button-enabled")).w()
        ) {
            Text(text = "About")
        }
    }
}

/*
*
* Previews
*
* */

@Composable
@Preview(name = "HomePage Preview - Light Mode")
private fun HomePagePreview() {
    MyApp {
        HomeScreen()
    }
}

@Composable
@Preview(
    name = "HomePage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun HomePageDarkPreview() {
    MyApp {
        HomeScreen()
    }
}