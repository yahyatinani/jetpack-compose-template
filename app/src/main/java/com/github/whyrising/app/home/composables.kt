package com.github.whyrising.app.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.whyrising.app.ui.theme.JetpackComposeTemplateTheme

fun helloText(name: String, color: Color) = buildAnnotatedString {
    val fontSize = 32.sp
    withStyle(
        SpanStyle(fontSize = fontSize)
    ) {
        append("Hello ")
    }

    withStyle(
        SpanStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    ) {
        append(name)
    }

    withStyle(
        SpanStyle(fontSize = fontSize)
    ) {
        append(" \uD83D\uDE00")
    }
}

@Composable
fun HomeScreen() {
    val androidApi = Build.VERSION.SDK_INT
    val color = MaterialTheme.colors.primaryVariant
    val greeting = remember { helloText("Android $androidApi", color) }
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = greeting,
                modifier = Modifier.padding(24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "15",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { }) {
                Text(text = "Count")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { },
                enabled = true
            ) {
                Text(text = "About")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    JetpackComposeTemplateTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ScreenDarkPreview() {
    JetpackComposeTemplateTheme {
        HomeScreen()
    }
}
