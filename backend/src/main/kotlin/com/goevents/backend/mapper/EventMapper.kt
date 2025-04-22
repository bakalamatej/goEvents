package com.goevents.backend.mapper

import com.goevents.backend.database.model.Event
import com.goevents.backend.dto.EventResponse

fun Event.toResponse(): EventResponse {
    return EventResponse(
        id = this.id.toHexString(),
        title = this.title,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate,
        location = this.location,
        eventType = this.eventType,
        imageUrl = this.imageUrl,
        link = this.link,
        createdAt = this.createdAt
    )
}