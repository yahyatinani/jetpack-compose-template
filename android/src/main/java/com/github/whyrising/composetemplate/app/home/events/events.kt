package com.github.whyrising.composetemplate.app.home.events

import com.github.whyrising.composetemplate.app.Page
import com.github.whyrising.composetemplate.app.home.db.AppDbSchema
import com.github.whyrising.recompose.regEventDb

fun regHomePageEvents() {
    regEventDb<AppDbSchema>(":homePage") { db, event ->
        val (_, topBarTitle, currentPage) = event
        db.copy(
            topBarTitle = "$topBarTitle",
            activePage = currentPage as Page,
            isNavButtonEnabled = true
        )
    }

    regEventDb<AppDbSchema>(":inc") { db, _ ->
        db.copy(counter = db.counter + 1)
    }
}
