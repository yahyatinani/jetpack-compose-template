package com.why.template.compose.recompose.db

//import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue

/**
 * ------------------ Application State ---------------
 *
 * Should not be accessed directly by application code.
 *
 * Read access goes through subscriptions.
 *
 * Updates via event handlers.
 *
 * */
private val appdb = mutableStateOf(MainViewModel())

var appDb: MainViewModel
    get() {
        return appdb.value.copy()
    }
    set(value) {
        appdb.value = value
    }
