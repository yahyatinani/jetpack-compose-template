package com.github.whyrising.app.global

import com.github.whyrising.app.Keys.format_screen_title
import com.github.whyrising.app.Keys.screen_title
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.y.collections.core.v

fun regGlobalSubs() {
    regSub<DbSchema, String>(
        queryId = screen_title,
    ) { db, _ ->
        db.screenTitle
    }

    regSub<String, String>(
        queryId = format_screen_title,
        signalsFn = {
            subscribe(v(screen_title))
        }
    ) { title, _ ->
        title.replaceFirstChar { it.uppercase() }
    }
}
