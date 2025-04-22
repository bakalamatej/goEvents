package com.goevents.backend.database.model

import com.goevents.backend.enums.AccountType
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    val email: String,
    val hashedPassword: String,
    val accountType: AccountType = AccountType.USER,
    @Id val id: ObjectId = ObjectId()
)