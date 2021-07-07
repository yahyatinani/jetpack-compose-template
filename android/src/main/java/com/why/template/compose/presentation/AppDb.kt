package com.why.template.compose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

object AppDb : ViewModel() {
    var topBarTitle by mutableStateOf("")
        internal set

    var currentRoute by mutableStateOf(Routes.HOME)
        internal set
}
