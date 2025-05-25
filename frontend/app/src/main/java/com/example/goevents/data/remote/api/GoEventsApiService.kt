package com.example.goevents.data.remote.api

import com.example.goevents.data.remote.dto.EventResponse
import retrofit2.http.GET

interface GoEventsApiService {
    @GET("/events")
    suspend fun getAllEvents(): List<EventResponse>
}