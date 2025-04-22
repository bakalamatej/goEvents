package com.goevents.backend.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "saved_events")
data class SavedEvent(
    @Id val id: ObjectId = ObjectId.get(),
    val userId: ObjectId,
    val eventId: ObjectId
)