package com.example.goevents.data.remote.dto

import com.example.goevents.domain.model.EventType
import java.util.Date

data class EventRequest(
    val id: String? = null,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val eventType: EventType,
    val imageUrl: String? = null,
    val link: String? = null
)