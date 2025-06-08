package com.example.goevents.data.repository

import com.example.goevents.data.remote.api.GoEventsApiService
import com.example.goevents.data.remote.dto.LoginRequest
import com.example.goevents.data.remote.dto.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: GoEventsApiService
) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}