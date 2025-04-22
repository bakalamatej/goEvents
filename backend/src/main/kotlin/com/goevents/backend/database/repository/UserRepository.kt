package com.goevents.backend.database.repository

import com.goevents.backend.database.model.User
import com.goevents.backend.enums.AccountType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}