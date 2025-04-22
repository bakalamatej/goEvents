package com.goevents.backend.database.model

import com.goevents.backend.enums.EventType
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.Date

@Document("events")
data class Event (
    val title: String,
    val description: String,
    val startDate: Date,
    val endDate: Date,
    val location: String,
    val eventType: EventType,
    val imageUrl: String? = null,
    val link: String? = null,
    val createdAt: Instant,
    val ownerId: ObjectId,
    @Id val id: ObjectId = ObjectId.get()
)