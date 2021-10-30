package com.github.whyrising.app.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.whyrising.app.ui.theme.JetpackComposeTemplateTheme

@Composable
fun AboutScreen(apiVersion: Int = -1) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "This app is a base for building new Compose apps.")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Android API: $apiVersion",
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    JetpackComposeTemplateTheme {
        AboutScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScreenDarkPreview() {
    JetpackComposeTemplateTheme {
        AboutScreen()
    }
}
