package com.sahar.aflamy.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    protected val _errorState: MutableState<String?> = mutableStateOf(null)
    val errorState: State<String?> = _errorState

}