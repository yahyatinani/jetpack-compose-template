package com.github.whyrising.composetemplate.app.home.subs

import com.github.whyrising.composetemplate.app.home.db.DbSchema
import com.github.whyrising.recompose.regSub

fun regHomePageSubs() {
    regSub<DbSchema, String>(":counter") { db, _ ->
        "${db.home.counter}"
    }

    regSub<DbSchema, Boolean>(":is-nav-button-enabled") { db, _ ->
        db.home.isNavButtonEnabled
    }
}
