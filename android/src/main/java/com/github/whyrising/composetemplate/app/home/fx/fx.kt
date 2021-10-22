package com.github.whyrising.composetemplate.app.home.fx

import com.github.whyrising.composetemplate.app.home.db.AppDbSchema
import com.github.whyrising.recompose.fx.FxIds.dispatch
import com.github.whyrising.recompose.fx.FxIds.fx
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.recompose.schemas.Schema.db
import com.github.whyrising.y.collections.core.get
import com.github.whyrising.y.collections.core.m
import com.github.whyrising.y.collections.core.v

fun regHomePageFx() {
    regEventFx(":navigate") { cofx, (_, route) ->
        val appDb = get(cofx, db) as AppDbSchema
        m(
            db to appDb.copy(isNavButtonEnabled = false),
            fx to v(
                v(":navigate!", route),
                v(dispatch, v(":inc"))
            )
        )
    }
}
