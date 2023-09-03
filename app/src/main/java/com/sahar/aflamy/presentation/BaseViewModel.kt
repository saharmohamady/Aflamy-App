package com.sahar.aflamy.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * Should handle all common functionalities between app viewModels
 */
open class BaseViewModel : ViewModel() {
    /**
     * Should represent the error happened, null means no error.
     */
    val errorState: MutableState<String?> = mutableStateOf(null)

}