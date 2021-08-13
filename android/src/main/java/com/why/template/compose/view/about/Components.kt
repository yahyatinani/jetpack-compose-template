package com.why.template.compose.view.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.events.event
import com.why.template.compose.R
import com.why.template.compose.presentation.Route
import com.why.template.compose.view.common.MyApp

@Composable
fun AboutPage(apiVersion: Int = -1) {
    val title = stringResource(R.string.top_bar_about_title)
    LaunchedEffect(true) {
        dispatch(event(id = ":pageInfoEvent", title, Route.ABOUT))
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is a Jetpack Compose app template.")
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Android API: $apiVersion",
            color = MaterialTheme.colors.primary
        )
    }
}

/*
*
* Previews
*
* */

@Composable
private fun AboutPageWithTheme() {
    MyApp {
        AboutPage()
    }
}

@Composable
@Preview(name = "AboutPage Preview - Light Mode")
private fun AboutPagePreview() {
    AboutPageWithTheme()
}

@Composable
@Preview(
    name = "AboutPage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun AboutPageDarkPreview() {
    AboutPageWithTheme()
}
