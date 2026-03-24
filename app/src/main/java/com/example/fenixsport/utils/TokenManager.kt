package com.example.fenixsport.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object TokenManager {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    fun saveToken(value: String) {
        _token.value = value
    }

    fun clearToken() {
        _token.value = null
    }

    fun isLoggedIn(): Boolean = _token.value != null
}