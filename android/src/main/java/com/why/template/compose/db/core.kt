package com.why.template.compose.db

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.why.template.compose.presentation.Route

class AppDb(
    topBarTitle: String = "",
    currentRoute: Route = Route.HOME
) : ViewModel() {
    var topBarTitle by mutableStateOf(topBarTitle)
    var currentRoute by mutableStateOf(currentRoute)
}

val appDb = AppDb()
