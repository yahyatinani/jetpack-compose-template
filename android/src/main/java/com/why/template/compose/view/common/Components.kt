package com.why.template.compose.view.common

import android.content.res.Configuration
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.why.template.compose.theme.MyTheme
import com.why.template.compose.view.home.topAppBar

@Composable
fun MyApp(topAppBarText: String = "Title", content: @Composable () -> Unit) {
    MyTheme {
        Scaffold(topBar = topAppBar(topAppBarText)) {
            Surface {
                content()
            }
        }
    }
}

/*
*
* Previews
*
* */

@Composable
@Preview(name = "MyApp Preview - Light Mode")
fun MyAppPreview() {
    MyApp {}
}

@Composable
@Preview(
    name = "MyApp Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun MyAppDarkPreview() {
    MyApp {}
}
