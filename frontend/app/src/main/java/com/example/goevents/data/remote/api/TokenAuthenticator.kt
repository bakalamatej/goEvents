package com.example.goevents.data.remote.api

import com.example.goevents.data.preferences.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Route
import java.net.Authenticator

class TokenAuthenticator(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
) : Authenticator(), okhttp3.Authenticator {

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val refreshToken = runBlocking { tokenManager.getRefreshToken() } ?: return null

        val newAccessToken = runBlocking {
            val refreshResponse = apiService.refresh(refreshToken)
            if (refreshResponse.isSuccessful) {
                val body = refreshResponse.body()
                body?.let {
                    tokenManager.saveTokens(it.accessToken, it.refreshToken)
                    it.accessToken
                }
            } else null
        } ?: return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}