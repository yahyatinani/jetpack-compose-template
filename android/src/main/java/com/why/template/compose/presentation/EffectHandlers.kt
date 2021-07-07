package com.why.template.compose.presentation

fun updateTopBarTitle(title: String) {
    AppDb.topBarTitle = title
}

fun updateCurrentRoute(route: Routes) {
    AppDb.currentRoute = route
}
