package com.why.template.compose.presentation

enum class Routes {
    HOME,
    ABOUT;

    companion object {
        fun toRoute(route: String?): Routes =
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
