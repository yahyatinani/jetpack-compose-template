package com.why.template.compose

import android.app.Application
import com.why.template.compose.view.common.init

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        init()
    }
}
