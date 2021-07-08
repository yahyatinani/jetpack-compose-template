package com.why.template.compose.db

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.why.template.compose.presentation.Route

data class AppDb(
    var topBarTitle: MutableState<String> = mutableStateOf(""),
    var currentRoute: MutableState<Route> = mutableStateOf(Route.HOME)
) : ViewModel()

val appDb = AppDb()
