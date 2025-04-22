package com.goevents.backend.security

import com.goevents.backend.database.repository.UserRepository
import com.goevents.backend.enums.AccountType
import org.bson.types.ObjectId

class UserService(
    private val userRepository: UserRepository
) {

    fun changeAccountType(userId: ObjectId, newAccountType: AccountType) {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User not found.")
        }
        val updatedUser = user.copy(accountType = newAccountType)
        userRepository.save(updatedUser)
    }
}