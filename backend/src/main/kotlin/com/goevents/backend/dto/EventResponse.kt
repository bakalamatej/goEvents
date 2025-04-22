package com.goevents.backend.dto

import com.goevents.backend.enums.EventType
import java.time.Instant
import java.util.Date

data class EventResponse (
    val id: String,
    val title: String,
    val description: String,
    val startDate: Date,
    val endDate: Date,
    val location: String,
    val eventType: EventType,
    val imageUrl: String?,
    val link: String?,
    val createdAt: Instant
)
