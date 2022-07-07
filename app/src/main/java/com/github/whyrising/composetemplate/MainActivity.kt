package com.github.whyrising.composetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.whyrising.composetemplate.ui.theme.TemplateTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().apply {
      // TODO: remove this.
//       setKeepOnScreenCondition { true }
    }
    super.onCreate(savedInstanceState)

    setContent {
      TemplateTheme {
        // A surface container using the 'background' color from the theme
        Scaffold { paddingValues ->
          Surface(
            modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues),
            color = MaterialTheme.colors.background
          ) {
            Greeting("Android")
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  TemplateTheme {
    Greeting("Android")
  }
}
