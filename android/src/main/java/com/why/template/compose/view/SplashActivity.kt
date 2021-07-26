// TODO: Change the package
package com.why.template.compose.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.events.event
import com.github.whyrising.recompose.regEventDb
import com.why.template.compose.presentation.MainViewModel

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
