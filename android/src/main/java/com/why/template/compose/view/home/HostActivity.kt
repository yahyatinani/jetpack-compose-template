package com.why.template.compose.view.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.why.template.compose.view.common.MyApp

class HostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApp(topAppBarText = "Compose Template") {
                HomePage()
            }
        }
    }
}
