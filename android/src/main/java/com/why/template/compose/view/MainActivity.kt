package com.why.template.compose.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.setContent
import com.why.template.compose.theme.TemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TemplateTheme {
                Greeting("Android ${Build.VERSION.SDK_INT}")
            }
        }
    }
}
