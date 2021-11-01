package com.github.whyrising.app.home

import com.github.whyrising.recompose.fx.FxIds.fx
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.recompose.schemas.Schema.db
import com.github.whyrising.y.collections.core.get
import com.github.whyrising.y.collections.core.m
import com.github.whyrising.y.collections.core.v

fun regHomeEvents() {
    regEventDb<DbSchema>(":update-screen-title") { db, (_, title) ->
        db.copy(screenTitle = title as String)
    }
    regEventDb<DbSchema>(":enable-about-btn") { db, _ ->
        db.copy(home = db.home.copy(isAboutBtnEnabled = true))
    }

    regEventFx(":navigate") { cofx, (_, route) ->
        val appDb = get(cofx, db) as DbSchema
        val home = appDb.home.copy(isAboutBtnEnabled = false)
        m(
            db to appDb.copy(home = home),
            fx to v(
                v(":navigate!", route),
//                v(FxIds.dispatch, v(":inc"))
            )
        )
    }
}
