package com.goevents.backend.database.repository

import com.goevents.backend.database.model.SavedEvent
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SavedEventRepository: MongoRepository<SavedEvent, ObjectId> {
    fun findByUserId(userId: ObjectId): List<SavedEvent>
    fun findByUserIdAndEventId(userId: ObjectId, eventId: ObjectId): SavedEvent?
    fun deleteByUserIdAndEventId(userId: ObjectId, eventId: ObjectId)
}