package com.example.fenixsport.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fenixsport.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AuthUiState(
    val isLoggedIn: Boolean = false,
    val userEmail: String = "",
    val isAdmin: Boolean = false,
    val loginError: String? = null,
    val registerError: String? = null,
    val registerSuccess: String? = null
)

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AuthUiState(isLoggedIn = TokenManager.isLoggedIn())
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String): Boolean {
        val normalizedEmail = email.trim()
        val normalizedPassword = password.trim()

        if (normalizedEmail.isBlank() || normalizedPassword.isBlank()) {
            _uiState.update { it.copy(loginError = "Completa correo y contrasena.") }
            return false
        }

        if (!normalizedEmail.contains("@")) {
            _uiState.update { it.copy(loginError = "Correo invalido.") }
            return false
        }

        if (normalizedPassword.length < 6) {
            _uiState.update { it.copy(loginError = "La contrasena debe tener minimo 6 caracteres.") }
            return false
        }

        val isAdmin = normalizedEmail.equals("admin@fenixsport.com", ignoreCase = true)
        TokenManager.saveToken("jwt_${System.currentTimeMillis()}")

        _uiState.update {
            it.copy(
                isLoggedIn = true,
                userEmail = normalizedEmail,
                isAdmin = isAdmin,
                loginError = null,
                registerError = null
            )
        }

        return true
    }

    fun register(name: String, email: String, password: String, confirmPassword: String): Boolean {
        val normalizedName = name.trim()
        val normalizedEmail = email.trim()

        if (normalizedName.length < 3) {
            _uiState.update { it.copy(registerError = "El nombre debe tener minimo 3 caracteres.") }
            return false
        }

        if (!normalizedEmail.contains("@")) {
            _uiState.update { it.copy(registerError = "Correo invalido.") }
            return false
        }

        if (password.length < 6) {
            _uiState.update { it.copy(registerError = "La contrasena debe tener minimo 6 caracteres.") }
            return false
        }

        if (password != confirmPassword) {
            _uiState.update { it.copy(registerError = "Las contrasenas no coinciden.") }
            return false
        }

        _uiState.update {
            it.copy(
                registerError = null,
                registerSuccess = "Registro exitoso. Ahora puedes iniciar sesion."
            )
        }
        return true
    }

    fun clearMessages() {
        _uiState.update { it.copy(loginError = null, registerError = null, registerSuccess = null) }
    }

    fun logout() {
        TokenManager.clearToken()
        _uiState.value = AuthUiState()
    }
}