package com.example.goevents.data.remote.dto

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)