package com.why.template.compose.view.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.whyrising.recompose.regSub
import com.why.template.compose.R
import com.why.template.compose.data.Spec
import com.why.template.compose.view.theme.MyTheme

@Composable
fun TopBar(title: String, elevation: Dp = 1.dp) {
    TopAppBar(
        elevation = elevation,
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun BgImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.product_logo_compose_color_192),
        contentDescription = "",
        modifier = modifier,
        alignment = Alignment.Center,
        contentScale = ContentScale.None,
        alpha = 0.05f
    )
}

@Composable
fun MyApp(
    topBarTitle: String = "title",
    content: @Composable () -> Unit
) {
    MyTheme {
        Scaffold(
            topBar = {
                TopBar(title = topBarTitle)
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                BgImage(modifier = Modifier.fillMaxSize())
                regSub<Spec>(":counter") { db, _ ->
                    db.counter.toString()
                }
                regSub<Spec>(":current-page") { db, _ ->
                    db.currentPage
                }
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
