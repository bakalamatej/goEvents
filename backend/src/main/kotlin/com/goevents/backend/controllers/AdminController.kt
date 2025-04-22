package com.goevents.backend.controllers

import com.goevents.backend.database.repository.UserRepository
import com.goevents.backend.enums.AccountType
import com.goevents.backend.security.UserService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/admin")
class AdminController(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    data class ChangeAccountTypeRequest(
        val userId: String,
        val accountType: AccountType
    )

    @PostMapping("/change-account-type")
    fun changeAccountType(
        @RequestBody body: ChangeAccountTypeRequest
    ): ResponseEntity<String> {
        val currentUserId = SecurityContextHolder.getContext().authentication.principal as String

        val currentUser = userRepository.findById(ObjectId(currentUserId)).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found.")
        }
        if (currentUser.accountType != AccountType.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can change account types.")
        }
        return try {
            userService.changeAccountType(ObjectId(body.userId), body.accountType)
            ResponseEntity.ok("Account type updated successfully.")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change account type: ${e.message}")
        }
    }
}