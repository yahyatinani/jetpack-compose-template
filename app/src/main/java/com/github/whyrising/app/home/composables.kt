package com.github.whyrising.app.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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

@Composable
fun HomeScreen() {
    val title = stringResource(R.string.home_screen_title)
    SideEffect {
        dispatch(v(":update-screen-title", title))
        dispatch(v(":set-android-api"))
        dispatch(v(":enable-about-btn"))
    }

    val primaryColor = MaterialTheme.colors.primary
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subscribe<AnnotatedString>(
                    qvec = v(":android-greeting", primaryColor)
                ).w(),
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
