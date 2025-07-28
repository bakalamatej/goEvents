package com.example.goevents.data.repository

import com.example.goevents.data.remote.api.GoEventsApiService
import com.example.goevents.data.remote.dto.LoginRequest
import com.example.goevents.data.remote.dto.LoginResponse
import com.example.goevents.data.remote.dto.RegisterRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: GoEventsApiService
) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(email: String, name: String, password: String): Response<Void> {
        return api.register(RegisterRequest(email, name, password))
    }
}