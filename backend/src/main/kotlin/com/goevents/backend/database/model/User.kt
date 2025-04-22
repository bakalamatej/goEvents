package com.goevents.backend.database.model

import com.goevents.backend.enums.UserType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    val email: String,
    val hashedPassword: String,
    val userType: UserType = UserType.USER,
    @Id val id: ObjectId = ObjectId()
)