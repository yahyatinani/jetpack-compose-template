package com.why.template.compose.view.about

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.why.template.compose.R
import com.why.template.compose.event.dispatch
import com.why.template.compose.materialisedview.MainViewModel
import com.why.template.compose.presentation.Route
import com.why.template.compose.view.common.MyApp

@Composable
fun AboutPage(viewModel: MainViewModel, apiVersion: Int = -1) {
    Log.i("received-about-vm ", "$viewModel")

//    eventBus.post(AboutPageEvent(stringResource(R.string.top_bar_about_title)))

    dispatch(
        arrayListOf(
            ":pageViewModelEvent",
            stringResource(R.string.top_bar_about_title),
            Route.ABOUT
        )
    )

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
private fun AboutPageWithTheme() {
    MyApp(MainViewModel()) {
        AboutPage(MainViewModel())
    }
}

@Composable
@Preview(name = "AboutPage Preview - Light Mode")
private fun AboutPagePreview() {
    AboutPageWithTheme()
}

@Composable
@Preview(
    name = "AboutPage Preview - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun AboutPageDarkPreview() {
    AboutPageWithTheme()
}
