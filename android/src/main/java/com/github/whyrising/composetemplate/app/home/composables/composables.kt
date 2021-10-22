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
import com.github.whyrising.composetemplate.app.Page
import com.github.whyrising.composetemplate.app.Page.ABOUT
import com.github.whyrising.composetemplate.app.common.composables.MyApp
import com.github.whyrising.composetemplate.app.home.events.regHomePageEvents
import com.github.whyrising.composetemplate.app.home.fx.regHomePageFx
import com.github.whyrising.composetemplate.app.home.subs.regHomePageSubs
import com.github.whyrising.composetemplate.app.init
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
fun HomePage() {
    regHomePageEvents()
    regHomePageFx()
    regHomePageSubs()

    val title = stringResource(R.string.top_bar_home_title)
    LaunchedEffect(true) {
        Log.i("LaunchedEffect", "HomePage")
        dispatch(v(":homePage", title, Page.HOME, true))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val apiV = Build.VERSION.SDK_INT
        Greeting(name = "Android $apiV")
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = subscribe<String>(v(":counter")).w())

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { dispatch(v(":inc")) }) {
            Text(text = "Increase")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                dispatch(v(":navigate", "$ABOUT/$apiV"))
            },
            enabled = subscribe<Boolean>(v(":is-nav-button-enabled")).w()
        ) {
            Text(text = "Navigate")
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
    init()

    MyApp {
        HomePage()
    }
}

@Composable
@Preview(
    name = "HomePage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun HomePageDarkPreview() {
    init()

    MyApp {
        HomePage()
    }
}
