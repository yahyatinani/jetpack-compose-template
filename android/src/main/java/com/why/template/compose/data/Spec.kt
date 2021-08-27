package com.why.template.compose.data

data class Spec(
    val topBarTitle: String = "",
    val counter: Int = 0,
    val currentPage: Route = Route.NONE,
    val navigateButtonFlag: Boolean = true
)
