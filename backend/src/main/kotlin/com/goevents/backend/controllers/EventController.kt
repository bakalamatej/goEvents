package com.goevents.backend.controllers

import com.goevents.backend.database.model.Event
import com.goevents.backend.database.repository.EventRepository
import com.goevents.backend.enums.EventType
import com.goevents.backend.dto.EventResponse
import com.goevents.backend.mapper.toResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.bson.types.ObjectId
import org.hibernate.validator.constraints.URL
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.Date

@RestController
@RequestMapping("/events")
class EventController (
    private  val repository: EventRepository
){
    data class EventRequest (
        val id: String?,
        @field:NotBlank(message = "Title must not be blank.")
        val title: String,
        @field:NotBlank(message = "Description must not be blank.")
        val description: String,
        @field:FutureOrPresent(message = "Start date must be in the future or present.")
        val startDate: Date,
        @field:NotNull(message = "End date must not be null.")
        val endDate: Date,
        @field:NotBlank(message = "Location must not be blank.")
        val location: String,
        @field:NotNull(message = "Event type must not be null.")
        val eventType: EventType,
        @field:URL(message = "Invalid image URL format.")
        val imageUrl: String?,
        @field:URL(message = "Invalid URL format.")
        val link: String?,
    ){
        @get:AssertTrue(message = "End date must be after start date.")
        val isEndDateAfterStartDate: Boolean
            get() = endDate.after(startDate)
    }

    @PostMapping
    fun save(
        @Valid @RequestBody body: EventRequest
    ): EventResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        val event = repository.save(
            Event(
                id = body.id?.let {ObjectId(it)} ?: ObjectId.get(),
                title = body.title,
                description = body.description,
                startDate = body.startDate,
                endDate = body.endDate,
                location = body.location,
                eventType = body.eventType,
                imageUrl = body.imageUrl,
                link = body.link,
                createdAt = Instant.now(),
                ownerId = ObjectId(ownerId)
            )
        )
        return event.toResponse()
    }

    @PutMapping("/{id}")
    fun updateEvent(
        @PathVariable id: String,
        @Valid @RequestBody updatedEvent: EventRequest
    ): ResponseEntity<String> {
        return try {
            val objectId = ObjectId(id)
            val ownerId = SecurityContextHolder.getContext().authentication.principal as String
            val existingEvent = repository.findById(objectId).orElse(null)

            if (existingEvent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.")
            }
            if (existingEvent.ownerId.toHexString() != ownerId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to edit this event.")
            }
            val eventToUpdate = existingEvent.copy(
                title = updatedEvent.title,
                description = updatedEvent.description,
                startDate = updatedEvent.startDate,
                endDate = updatedEvent.endDate,
                location = updatedEvent.location,
                eventType = updatedEvent.eventType,
                imageUrl = updatedEvent.imageUrl,
                link = updatedEvent.link
            )
            repository.save(eventToUpdate)
            ResponseEntity.ok("Event updated successfully.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.")
        }
    }

    @GetMapping
    fun filterEvents(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) eventType: EventType?,
        @RequestParam(required = false) startDate: Date?
    ): List<EventResponse> {
        val events = repository.findAll()

        return events.filter { event ->
            (title == null || event.title.contains(title, ignoreCase = true)) &&
            (location == null || event.location.contains(location, ignoreCase = true)) &&
            (eventType == null || event.eventType == eventType) &&
            (startDate == null || event.startDate.after(startDate))
        }.map { it.toResponse() }
    }

    @GetMapping("/my")
    fun findByOwnerId(): List<EventResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<String> {
        return try {
            val objectId = ObjectId(id)
            val ownerId = SecurityContextHolder.getContext().authentication.principal as String
            val event = repository.findById(objectId).orElse(null)

            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.")
            }
            if (event.ownerId.toHexString() != ownerId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this event.")
            }
            repository.deleteById(objectId)
            ResponseEntity.ok("Event deleted successfully.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.")
        }
    }
}