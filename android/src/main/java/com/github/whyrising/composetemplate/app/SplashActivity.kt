// TODO: Change the package
package com.github.whyrising.composetemplate.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi

@ExperimentalAnimationApi
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, HostActivity::class.java))
        finish()
    }
}
