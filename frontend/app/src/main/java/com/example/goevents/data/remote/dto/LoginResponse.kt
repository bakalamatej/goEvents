package com.example.goevents.data.remote.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)