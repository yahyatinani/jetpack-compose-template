package com.github.whyrising.composetemplate.app.home.events

import com.github.whyrising.composetemplate.app.home.db.DbSchema
import com.github.whyrising.composetemplate.app.home.db.defaultDb
import com.github.whyrising.recompose.events.Event
import com.github.whyrising.recompose.fx.FxIds
import com.github.whyrising.recompose.fx.FxIds.fx
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.recompose.schemas.Schema.db
import com.github.whyrising.y.collections.core.get
import com.github.whyrising.y.collections.core.m
import com.github.whyrising.y.collections.core.v

fun initDbHandler(db: Any, event: Event): DbSchema = defaultDb

fun regHomeScreenEvents() {
    regEventDb<DbSchema>(":homePage") { db, event ->
        val (_, topBarTitle) = event
        db.copy(
            screenTitle = "$topBarTitle",
            home = db.home.copy(isNavButtonEnabled = true)
        )
    }

    regEventDb<DbSchema>(":inc") { db, _ ->
        val home = db.home
        db.copy(home = home.copy(counter = home.counter + 1))
    }

    regEventFx(":navigate") { cofx, event ->
        val appDb = get(cofx, db) as DbSchema
        val home = appDb.home.copy(isNavButtonEnabled = false)
        m(
            db to appDb.copy(home = home),
            fx to v(
                v(":navigate!", event.subvec(1, event.count)),
                v(FxIds.dispatch, v(":inc"))
            )
        )
    }
}
