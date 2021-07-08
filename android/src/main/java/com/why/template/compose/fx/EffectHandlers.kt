package com.why.template.compose.fx

import com.why.template.compose.db.appDb
import com.why.template.compose.presentation.Route

fun updateTopBarTitle(title: String) {
    appDb.topBarTitle.value = title
}

fun updateCurrentRoute(route: Route) {
    appDb.currentRoute.value = route
}
