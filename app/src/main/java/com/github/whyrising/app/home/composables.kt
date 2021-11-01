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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.whyrising.app.R
import com.github.whyrising.app.Routes
import com.github.whyrising.app.initAppDb
import com.github.whyrising.app.ui.theme.JetpackComposeTemplateTheme
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.y.collections.core.v

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
    val color = MaterialTheme.colors.primary
    val greeting = remember {
        helloText("Android ${Build.VERSION.SDK_INT}", color)
    }

    val title = stringResource(R.string.home_screen_title)
    SideEffect {
        dispatch(v(":update-screen-title", title))
        dispatch(v(":enable-about-btn", title))
    }

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
                onClick = { dispatch(v(":navigate", Routes.about)) },
                enabled = subscribe<Boolean>(v(":is-about-btn-enabled")).w()
            ) {
                Text(text = "About")
            }
        }
    }
}

// -- Previews -----------------------------------------------------------------

private fun init() {
    initAppDb()
    regHomeEvents()
    regHomeSubs()
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    init()
    JetpackComposeTemplateTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ScreenDarkPreview() {
    init()
    JetpackComposeTemplateTheme {
        HomeScreen()
    }
}
