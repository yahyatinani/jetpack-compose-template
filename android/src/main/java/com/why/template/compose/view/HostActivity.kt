package com.why.template.compose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.why.template.compose.view.common.MyApp
import com.why.template.compose.view.home.HomePage

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
