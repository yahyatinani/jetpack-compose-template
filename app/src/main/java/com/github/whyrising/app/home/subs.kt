package com.github.whyrising.app.home

import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.y.collections.core.v

fun regHomeSubs() {
    regSub<DbSchema, String>(
        queryId = ":screen-title",
    ) { db, _ ->
        db.screenTitle
    }

    regSub<String, String>(
        queryId = ":format-screen-title",
        signalsFn = {
            subscribe(v(":screen-title"))
        }
    ) { title, _ ->
        title.replaceFirstChar { it.uppercase() }
    }

    regSub<DbSchema, Boolean>(
        queryId = ":is-about-btn-enabled",
    ) { db, _ ->
        db.home.isAboutBtnEnabled
    }
}
