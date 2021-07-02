package com.why.template.compose.view.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.why.template.compose.R
import com.why.template.compose.theme.MyTheme
import com.why.template.compose.view.home.topAppBar

@Composable
fun MyApp(topAppBarText: String = "Title", content: @Composable () -> Unit) {
    MyTheme {
        Scaffold(topBar = topAppBar(topAppBarText)) {
            Surface {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(
                            id = R.drawable.product_logo_compose_color_192
                        ),
                        contentDescription = "",
                        alpha = 0.05f
                    )
                    content()
                }
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
