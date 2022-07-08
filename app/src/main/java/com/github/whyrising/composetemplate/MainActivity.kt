package com.github.whyrising.composetemplate

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.whyrising.composetemplate.home.Home
import com.github.whyrising.composetemplate.ui.theme.BackArrow
import com.github.whyrising.composetemplate.ui.theme.TemplateTheme

@Composable
fun Main() {
  TemplateTheme {
    // A surface container using the 'background' color from the theme
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Text(text = "Home")
          },
          navigationIcon = if (true) null else { // TODO:
            { BackArrow() }
          }
        )
      },
    ) { paddingValues ->
      Home(modifier = Modifier.padding(paddingValues))
    }
  }
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().apply {
      // TODO: remove this.
//       setKeepOnScreenCondition { true }
    }
    super.onCreate(savedInstanceState)

    setContent {
      Main()
    }
  }
}

// -- Previews -----------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun MainPreview() {
  Main()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainDarkPreview() {
  Main()
}
