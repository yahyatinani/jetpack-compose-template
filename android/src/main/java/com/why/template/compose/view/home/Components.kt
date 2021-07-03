package com.why.template.compose.view.home

import android.content.res.Configuration
import android.os.Build
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        modifier = Modifier
            .padding(24.dp),
        text = helloText(name)
    )
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting("Android ${Build.VERSION.SDK_INT}")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /*TODO: Navigation library*/ }) {
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
fun HomePageWithTheme() {
    MyApp {
        HomePage()
    }
}

@Composable
@Preview(name = "HomePage Preview - Light Mode")
fun HomePagePreview() {
    HomePageWithTheme()
}

@Composable
@Preview(
    name = "HomePage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun HomePageDarkPreview() {
    HomePageWithTheme()
}
