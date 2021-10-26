package com.github.whyrising.composetemplate.app

import android.app.Application
import com.github.whyrising.composetemplate.app.home.events.initDbHandler
import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.y.collections.core.v

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        regEventDb(":initialize", handler = ::initDbHandler)
        dispatchSync(v(":initialize"))
    }
}
