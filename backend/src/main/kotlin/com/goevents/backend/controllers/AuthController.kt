package com.goevents.backend.controllers

import com.goevents.backend.security.AuthService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
){
    data class LoginRequest(
        @field:Email(message = "Invalid email format.")
        val email: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$",
            message = "Password must be at least 9 characters long and contain at least one digit, uppercase and lowercase character."
        )
        val password: String
    )

    data class RegisterRequest(
        @field:Email(message = "Invalid email format.")
        val email: String,
        val name: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$",
            message = "Password must be at least 9 characters long and contain at least one digit, uppercase and lowercase character."
        )
        val password: String
    )

    data class LogoutRequest(
        val refreshToken: String
    )

    data class RefreshRequest(
        val refreshToken: String
    )

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: RegisterRequest
    ) {
        authService.register(body.email, body.name, body.password)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginRequest
    ): AuthService.TokenPair {
        return authService.login(body.email, body.password)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody body: LogoutRequest
    ) {
        authService.logout(body.refreshToken)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): AuthService.TokenPair {
        return authService.refresh(body.refreshToken)
    }
}