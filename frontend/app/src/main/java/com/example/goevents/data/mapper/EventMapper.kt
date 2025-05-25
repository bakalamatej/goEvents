package com.example.goevents.data.mapper

import com.example.goevents.data.remote.dto.EventResponse
import com.example.goevents.domain.model.Event

fun EventResponse.toEvent(): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        location = location,
        eventType = eventType,
        imageUrl = imageUrl,
        link = link,
        createdAt = createdAt
    )
}