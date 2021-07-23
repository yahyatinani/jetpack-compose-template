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

fun reset(value: Any) {
    appDb = value
}
