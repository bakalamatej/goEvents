package com.goevents.backend.database.repository

import com.goevents.backend.database.model.User
import com.goevents.backend.enums.UserType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
    fun findByUserType(userType: UserType): List<User>
}