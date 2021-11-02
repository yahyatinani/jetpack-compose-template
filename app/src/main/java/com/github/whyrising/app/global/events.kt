package com.github.whyrising.app.global

import com.github.whyrising.app.Keys.navigate
import com.github.whyrising.app.Keys.navigateFx
import com.github.whyrising.app.Keys.update_screen_title
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.y.collections.core.m

fun regGlobalEvents() {
    regEventDb<DbSchema>(id = update_screen_title) { db, (_, title) ->
        db.copy(screenTitle = title as String)
    }

    regEventFx(id = navigate) { _, (_, route) ->
        m(navigateFx to route)
    }
}
