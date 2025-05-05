package com.example.goevents.domain.model

data class Event(
    val id: Int,
    val title: String,
    val description: String?,
    val location: String,
    val startDate: String,
    val endDate: String?,
    val eventType: String,
    val ownerId: Int
)