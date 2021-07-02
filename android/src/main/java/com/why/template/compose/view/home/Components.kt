package com.why.template.compose.view.home

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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

fun topAppBar(text: String) = @Composable {
    TopAppBar(
        elevation = 1.dp,
        title = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun helloText(name: String): AnnotatedString = buildAnnotatedString {
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
fun Greeting(name: String) {
    Text(text = helloText(name), modifier = Modifier.padding(24.dp))
}

@Composable
fun HomePage() {
    MyTheme {
        Scaffold(topBar = topAppBar("Compose Template")) {
            Surface {
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
    HomePage()
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GreetingDarkPreview() {
    HomePage()
}
