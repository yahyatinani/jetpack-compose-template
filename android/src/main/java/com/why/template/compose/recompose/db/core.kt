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
val appDb = mutableStateOf(MainViewModel())

var appState: MainViewModel
    get() {
        return appDb.value.copy()
    }
    set(value) {
        appDb.value = value
    }
