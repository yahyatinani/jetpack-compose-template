package com.why.template.compose.view.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.why.template.compose.view.common.MyApp

@Composable
fun AboutPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "This is a Jetpack Compose app template.",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AboutPageWithTheme() {
    MyApp {
        AboutPage()
    }
}

@Composable
@Preview(name = "AboutPage Preview - Light Mode")
fun HomePagePreview() {
    AboutPageWithTheme()
}

@Composable
@Preview(
    name = "AboutPage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun HomePageDarkPreview() {
    AboutPageWithTheme()
}