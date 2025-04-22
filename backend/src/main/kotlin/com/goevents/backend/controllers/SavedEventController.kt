package com.goevents.backend.controllers

import com.goevents.backend.database.model.SavedEvent
import com.goevents.backend.database.repository.EventRepository
import com.goevents.backend.database.repository.SavedEventRepository
import com.goevents.backend.dto.EventResponse
import com.goevents.backend.mapper.toResponse
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/saved-events")
class SavedEventController(
    private val savedEventRepository: SavedEventRepository,
    private val eventRepository: EventRepository
) {

    @PostMapping("/{eventId}")
    fun saveEvent(@PathVariable eventId: String): ResponseEntity<String> {
        return try {
            val userId = ObjectId(SecurityContextHolder.getContext().authentication.principal as String)
            val objectId = ObjectId(eventId)
            val event = eventRepository.findById(objectId).orElse(null)

            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.")
            }
            val alreadySaved = savedEventRepository.findByUserIdAndEventId(userId, objectId)
            if (alreadySaved != null) {
                return ResponseEntity.badRequest().body("Event already saved.")
            }

            savedEventRepository.save(SavedEvent(userId = userId, eventId = objectId))
            ResponseEntity.ok("Event saved.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.")
        }
    }

    @DeleteMapping("/{eventId}")
    fun unsaveEvent(@PathVariable eventId: String): ResponseEntity<String> {
        return try {
            val userId = ObjectId(SecurityContextHolder.getContext().authentication.principal as String)
            val objectId = ObjectId(eventId)
            val event = eventRepository.findById(objectId).orElse(null)

            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.")
            }
            savedEventRepository.deleteByUserIdAndEventId(userId, objectId)
            ResponseEntity.ok("Event unsaved.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.")
        }
    }

    @GetMapping
    fun getSavedEvents(): List<EventResponse> {
        val userId = ObjectId(SecurityContextHolder.getContext().authentication.principal as String)
        val savedEvents = savedEventRepository.findByUserId(userId)

        val eventIds = savedEvents.map { it.eventId }
        val events = eventRepository.findAllById(eventIds)

        return events.map { it.toResponse() }
    }
}