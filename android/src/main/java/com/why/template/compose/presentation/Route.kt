package com.why.template.compose.presentation

enum class Route {
    HOME,
    ABOUT,
    NONE;

    companion object {
        fun toRoute(route: String?): Route =
            when (route?.substringBefore("/")) {
                HOME.name -> HOME
                ABOUT.name -> ABOUT
                null -> HOME
                else -> throw IllegalArgumentException(
                    "Route $route is not recognized."
                )
            }
    }
}
