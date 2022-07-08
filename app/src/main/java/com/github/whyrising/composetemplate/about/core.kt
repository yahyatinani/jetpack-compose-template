package com.github.whyrising.composetemplate.about

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.whyrising.composetemplate.R
import com.github.whyrising.composetemplate.ui.theme.TemplateTheme

@Composable
fun About(modifier: Modifier = Modifier) {
  Surface(modifier = modifier.fillMaxSize()) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = "Jetpack Compose Template")
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "v${stringResource(id = R.string.app_version)}",
        style = MaterialTheme.typography.subtitle1
      )
    }
  }
}

// -- Previews -----------------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
  TemplateTheme {
    About()
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutDarkPreview() {
  TemplateTheme {
    About()
  }
}
