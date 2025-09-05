package com.example.goevents.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goevents.data.preferences.TokenManager
import com.example.goevents.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val tokenManager: TokenManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _isRegistering = MutableStateFlow(false)
    val isRegistering: StateFlow<Boolean> = _isRegistering

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError: StateFlow<String?> = _registerError

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess

    fun login(email: String, password: String) {
        _isLoggingIn.value = true
        _loginError.value = null
        _loginSuccess.value = false

        viewModelScope.launch {
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {
                    val tokenAccess = response.body()?.accessToken
                    val tokenRefresh = response.body()?.refreshToken
                    if (!tokenAccess.isNullOrBlank() && !tokenRefresh.isNullOrBlank()) {
                        tokenManager.saveTokens(tokenAccess, tokenRefresh)

                        _isLoggedIn.value = true
                        _loginSuccess.value = true
                    } else {
                        _loginError.value = "Invalid server response"
                    }
                } else {
                    _loginError.value = "Invalid credentials"
                }
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Login failed"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }

    fun register(email: String, name: String, password: String) {
        _isRegistering.value = true
        _registerError.value = null
        _registerSuccess.value = false

        viewModelScope.launch {
            try {
                val response = authRepository.register(email, name, password)
                if (response.isSuccessful) {
                    _registerSuccess.value = true
                } else {
                    _registerError.value = response.errorBody()?.string() ?: "Registration failed"
                }
            } catch (e: Exception) {
                _registerError.value = e.message ?: "Registration failed"
            } finally {
                _isRegistering.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val success = authRepository.logout()
            _logoutSuccess.value = success
        }
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            val token = tokenManager.getAccessToken()
            _isLoggedIn.value = !token.isNullOrBlank()
        }
    }

    fun setLoginSuccess(success: Boolean) {
        _loginSuccess.value = success
    }

    fun resetLoginSuccess() {
        _loginSuccess.value = false
    }

    fun setLoginError(message: String) {
        _loginError.value = message
    }

    fun resetRegisterSuccess() {
        _registerSuccess.value = false
    }

    fun setRegisterError(message: String) {
        _registerError.value = message
    }
}