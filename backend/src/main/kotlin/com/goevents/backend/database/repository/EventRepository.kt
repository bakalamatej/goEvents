package com.goevents.backend.database.repository

import com.goevents.backend.database.model.Event
import com.goevents.backend.enums.EventType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.sql.Date

interface EventRepository : MongoRepository<Event, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<Event>
}