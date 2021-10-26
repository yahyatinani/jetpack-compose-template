package com.github.whyrising.composetemplate.app.common.subs

import com.github.whyrising.composetemplate.app.home.db.DbSchema
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.y.collections.core.v


fun registerCommonSubs() {
    regSub<DbSchema, String>(":screen-title") { db, _ ->
        db.screenTitle
    }

    regSub(
        queryId = ":uppercase-title",
        signalsFn = { subscribe<String>(v(":screen-title")) }
    ) { title, _ ->
        title.uppercase()
    }
}
