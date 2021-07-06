package com.why.template.compose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

object Db : ViewModel() {
    var topBarTitle by mutableStateOf("")
        internal set
}
