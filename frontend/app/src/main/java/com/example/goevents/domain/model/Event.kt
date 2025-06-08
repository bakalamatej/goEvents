package com.example.goevents.domain.model

import java.util.Date

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val eventType: EventType,
    val imageUrl: String?,
    val link: String?,
    val createdAt: Date
)