package com.why.template.compose.recompose.db

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


/**
 * ------------------ Application State ---------------
 *
 * Should not be accessed directly by application code.
 *
 * Read access goes through subscriptions.
 *
 * Updates via event handlers.
 *
 * At the start, It contains a placeholder value `0` until an init event gets
 * dispatched.
 *
 * */
var appDb by mutableStateOf<Any>(0)
    private set

inline fun <reified R> appDb(): R = when (appDb) {
    is R -> appDb as R
    else -> {
        val message = "`appDb` is not initialized yet! Use regDbInit() to " +
                "register an init event and remember to dispatch it at the " +
                "start of your app."
        throw IllegalStateException(message)
    }
}

fun reset(value: Any) {
    appDb = value
}
