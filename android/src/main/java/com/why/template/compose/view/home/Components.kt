@file:Suppress("UnstableApiUsage")

package com.why.template.compose.view.home

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.why.template.compose.R
import com.why.template.compose.event.dispatch
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.presentation.NavigateEvent
import com.why.template.compose.presentation.Route
import com.why.template.compose.view.common.MyApp

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
fun HomePage(viewModel: MainViewModel) {
    Log.i("received-home-vm ", "$viewModel")

    dispatch(
        arrayListOf(
            ":pageViewModelEvent",
            stringResource(R.string.top_bar_home_title),
            Route.HOME
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val apiV = Build.VERSION.SDK_INT
        Greeting(name = "Android $apiV")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            dispatch(NavigateEvent("${Route.ABOUT}/$apiV"))
        }) {
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
private fun HomePageWithTheme() {
    MyApp(MainViewModel()) {
        HomePage(MainViewModel())
    }
}

@Composable
@Preview(name = "HomePage Preview - Light Mode")
private fun HomePagePreview() {
    HomePageWithTheme()
}

@Composable
@Preview(
    name = "HomePage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun HomePageDarkPreview() {
    HomePageWithTheme()
}
