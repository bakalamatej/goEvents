package com.example.goevents.data.repository

import com.example.goevents.data.preferences.TokenManager
import com.example.goevents.data.remote.api.ApiService
import com.example.goevents.data.remote.dto.LoginRequest
import com.example.goevents.data.remote.dto.TokenPair
import com.example.goevents.data.remote.dto.LogoutRequest
import com.example.goevents.data.remote.dto.RegisterRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Response<TokenPair> {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(email: String, name: String, password: String): Response<Void> {
        return api.register(RegisterRequest(email, name, password))
    }

    suspend fun logout(): Boolean {
        val refreshToken = tokenManager.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) return false

        return try {
            val response = api.logout(LogoutRequest(refreshToken))
            if (response.isSuccessful) {
                tokenManager.clearTokens()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}