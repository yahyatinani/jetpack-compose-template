package com.github.whyrising.composetemplate.app.home.subs

import com.github.whyrising.composetemplate.app.home.db.AppDbSchema
import com.github.whyrising.recompose.regSub

fun regHomePageSubs() {
    regSub<AppDbSchema, String>(":counter") { db, _ ->
        db.counter.toString()
    }

    regSub<AppDbSchema, Boolean>(":is-nav-button-enabled") { db, _ ->
        db.isNavButtonEnabled
    }
}
