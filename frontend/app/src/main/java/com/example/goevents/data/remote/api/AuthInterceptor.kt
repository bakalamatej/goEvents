package com.example.goevents.data.remote.api

import coil.intercept.Interceptor
import com.example.goevents.data.preferences.TokenManager
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        /*val requestBuilder = chain.request().newBuilder()
        tokenManager.getAccessToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
        */
        val accessToken = tokenManager.getAccessToken()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", accessToken)
            .build()
        val response = chain.proceed(request)
        if (response.code() == 401) {
            val newToken: String = //fetch from some other source
                if (newToken != null) {
                    val newRequest =  chain.request().newBuilder()
                        .addHeader("Authorization", newToken)
                        .build()
                    return chain.proceed(newRequest)
                }
        }
        return response
    }
}