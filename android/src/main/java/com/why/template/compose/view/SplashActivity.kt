// TODO: Change the package
package com.why.template.compose.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.why.template.compose.view.common.init

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        startActivity(Intent(this, HostActivity::class.java))
        finish()
    }
}
