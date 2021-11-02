package com.github.whyrising.app.global

import com.github.whyrising.app.home.DbHomeSchema
import com.github.whyrising.app.home.defaultDbHomeSchema

data class DbSchema(
    val screenTitle: String,
    val home: DbHomeSchema,
)

val defaultDb = DbSchema(
    screenTitle = "",
    home = defaultDbHomeSchema,
)
