package com.why.template.compose.presentation

import com.why.template.compose.db.appDb

fun topBarTitle(): String = appDb.topBarTitle

fun currentRoute(): String = appDb.currentRoute.name
