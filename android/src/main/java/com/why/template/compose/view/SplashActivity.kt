// TODO: Change the package
package com.why.template.compose.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.why.template.compose.view.home.HostActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, HostActivity::class.java))
        finish()
    }
}
