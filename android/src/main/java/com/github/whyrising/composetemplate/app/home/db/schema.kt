package com.github.whyrising.composetemplate.app.home.db

import com.github.whyrising.composetemplate.app.Screens.HOME

data class HomeData(
    val counter: Int,
    val isNavButtonEnabled: Boolean
)

data class DbSchema(
    val topBarTitle: String,
    val activeScreen: String,
    val home: HomeData
)

val defaultDb = DbSchema(
    topBarTitle = "",
    activeScreen = "$HOME",
    home = HomeData(
        counter = 0,
        isNavButtonEnabled = true
    )
)
