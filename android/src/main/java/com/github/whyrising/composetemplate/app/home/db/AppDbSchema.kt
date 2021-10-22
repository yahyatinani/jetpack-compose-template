package com.github.whyrising.composetemplate.app.home.db

import com.github.whyrising.composetemplate.app.Page
import com.github.whyrising.composetemplate.app.Page.ABOUT
import com.github.whyrising.composetemplate.app.Page.HOME

data class AppDbSchema(
    val topBarTitle: String = "",
    val activePage: Page = HOME,
    val homeRoute: String = "$HOME",
    val aboutRoute: String = "$ABOUT/{api-version}",
    val counter: Int = 0,
    val isNavButtonEnabled: Boolean = true
)
