package com.example.goevents.data.repository

import com.example.goevents.data.mapper.toEvent
import com.example.goevents.data.remote.api.ApiService
import com.example.goevents.domain.model.Event
import com.example.goevents.data.remote.dto.EventRequest
import retrofit2.Response
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getAllEvents(): List<Event> {
        return api.getAllEvents().map { it.toEvent() }
    }

    suspend fun createEvent(eventRequest: EventRequest): Response<Void> {
        return api.createEvent(eventRequest)
    }

    suspend fun getFilteredEvents(
        title: String? = null,
        location: String? = null,
        eventType: String? = null,
        startDate: String? = null
    ): List<Event> {
        return api.getFilteredEvents(title, location, eventType, startDate)
            .map {it.toEvent()}
    }
}