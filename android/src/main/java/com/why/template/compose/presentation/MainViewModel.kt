package com.why.template.compose.presentation

data class MainViewModel(
    val topBarTitle: String = "",
    val counter: Int = 0,
    val currentPage: Route = Route.NONE,
    val navigateButtonFlag: Boolean = true
)
