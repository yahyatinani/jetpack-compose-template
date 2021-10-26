package com.github.whyrising.composetemplate.app.home.db

import com.github.whyrising.composetemplate.app.Screens.HOME

data class HomeData(
    val counter: Int,
    val isNavButtonEnabled: Boolean
)

data class DbSchema(
    val screenTitle: String,
    val activeScreen: String,
    val home: HomeData
)

val defaultDb = DbSchema(
    screenTitle = "",
    activeScreen = "$HOME",
    home = HomeData(
        counter = 0,
        isNavButtonEnabled = true
    )
)
