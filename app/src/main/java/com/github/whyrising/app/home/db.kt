package com.github.whyrising.app.home

data class DbHomeSchema(val isAboutBtnEnabled: Boolean)

data class DbSchema(
    val screenTitle: String,
    val home: DbHomeSchema
)

val defaultDb = DbSchema(
    screenTitle = "",
    home = DbHomeSchema(isAboutBtnEnabled = true)
)
