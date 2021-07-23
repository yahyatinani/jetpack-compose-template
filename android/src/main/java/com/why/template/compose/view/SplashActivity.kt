// TODO: Change the package
package com.why.template.compose.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.why.template.compose.presentation.MainViewModel
import com.why.template.compose.recompose.dispatchSync
import com.why.template.compose.recompose.events.event
import com.why.template.compose.recompose.regEventDb

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regEventDb(":initialize") { _, _ ->
            MainViewModel()
        }

        dispatchSync(event(":initialize"))

        startActivity(Intent(this, HostActivity::class.java))
        finish()
    }
}
