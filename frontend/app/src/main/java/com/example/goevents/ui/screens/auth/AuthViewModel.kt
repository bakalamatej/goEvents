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
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    fun login(email: String, password: String) {
        _isLoggingIn.value = true
        _loginError.value = null

        viewModelScope.launch {
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {
                    val tokenAccess = response.body()?.accessToken
                    val tokenRefresh = response.body()?.refreshToken
                    if (!tokenAccess.isNullOrBlank() && !tokenRefresh.isNullOrBlank()) {
                        tokenManager.saveTokens(tokenAccess, tokenRefresh)
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
}