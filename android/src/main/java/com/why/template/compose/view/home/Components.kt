package com.why.template.compose.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.why.template.compose.theme.MyTheme

@Composable
fun helloText(name: String): AnnotatedString =
    buildAnnotatedString {
        append("Hello ")
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
        ) {
            append(name)
        }
        append(" \uD83D\uDE01")
    }

@Composable
fun TopAppBar() {
    TopAppBar(
        elevation = 1.dp,
        title = {
            Text(
                text = "Compose Template",
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun Greeting(name: String) {
    Scaffold(
        topBar = { TopAppBar() }
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = helloText(name))
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /*TODO: Navigation library*/ }) {
                    Text(text = "Navigate")
                }
            }
        }
    }
}

/**
 *
 * Previews
 *
 * */

@Composable
@Preview
fun GreetingPreview() {
    MyTheme {
        Greeting(name = "Jetpack Compose")
    }
}

@Composable
@Preview
fun GreetingDarkPreview() {
    MyTheme(isDarkTheme = true) {
        Greeting(name = "Jetpack Compose")
    }
}
