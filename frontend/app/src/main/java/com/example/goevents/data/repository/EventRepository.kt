package com.example.goevents.data.repository

import com.example.goevents.data.mapper.toEvent
import com.example.goevents.data.remote.api.GoEventsApiService
import com.example.goevents.domain.model.Event
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val api: GoEventsApiService
) {
    suspend fun getAllEvents(): List<Event> {
        return api.getAllEvents().map { it.toEvent() }
    }
}