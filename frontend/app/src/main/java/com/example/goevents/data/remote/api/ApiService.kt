package com.example.goevents.data.remote.api

import com.example.goevents.data.remote.dto.EventRequest
import com.example.goevents.data.remote.dto.EventResponse
import com.example.goevents.data.remote.dto.LoginRequest
import com.example.goevents.data.remote.dto.TokenPair
import com.example.goevents.data.remote.dto.LogoutRequest
import com.example.goevents.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/events")
    suspend fun getAllEvents(): List<EventResponse>

    @POST("/events")
    suspend fun createEvent(@Body eventRequest: EventRequest): Response<Void>

    @GET("events")
    suspend fun getFilteredEvents(
        @Query("title") title: String? = null,
        @Query("location") location: String? = null,
        @Query("eventType") eventType: String? = null,
        @Query("startDate") startDate: String? = null
    ): List<EventResponse>

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokenPair>

    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Void>

    @POST("/auth/logout")
    suspend fun logout(@Body logoutRequest: LogoutRequest): Response<Void>

    @POST("/auth/refresh")
    suspend fun refresh(@Body refreshToken: String): Response<TokenPair>
}