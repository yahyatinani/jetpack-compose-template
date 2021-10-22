package com.github.whyrising.composetemplate.app.about.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.whyrising.composetemplate.app.Page
import com.github.whyrising.composetemplate.app.common.composables.MyApp
import com.github.whyrising.composetemplate.app.home.db.AppDbSchema
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.y.collections.core.v
import com.why.template.compose.R

fun regAboutPageEvents() {
    regEventDb<AppDbSchema>(":aboutPage") { db, event ->
        val (_, topBarTitle, currentPage) = event

        db.copy(
            topBarTitle = "$topBarTitle",
            activePage = currentPage as Page
        )
    }
}

@Composable
fun AboutPage(apiVersion: Int = -1) {
    regAboutPageEvents()

    val title = stringResource(R.string.top_bar_about_title)
    LaunchedEffect(true) {
        dispatch(v(":aboutPage", title, Page.ABOUT))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is a Jetpack Compose app template.")
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Android API: $apiVersion",
            color = MaterialTheme.colors.primary
        )
    }
}

/*
*
* Previews
*
* */

@Composable
@Preview(name = "AboutPage Preview - Light Mode")
private fun AboutPagePreview() {
    MyApp {
        AboutPage()
    }
}

@Composable
@Preview(
    name = "AboutPage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun AboutPageDarkPreview() {
    MyApp {
        AboutPage()
    }
}
