package com.why.template.compose.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.setContent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Greeting("Android ${Build.VERSION.SDK_INT}")
        }
    }
}
