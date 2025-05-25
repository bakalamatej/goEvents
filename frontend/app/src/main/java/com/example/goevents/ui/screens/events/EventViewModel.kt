package com.example.goevents.ui.screens.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goevents.domain.model.Event
import com.example.goevents.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        refreshEvents()
    }

    fun refreshEvents() {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.value = repository.getAllEvents()
                _error.value = null
            } catch (e: Exception) {
                //_error.value = "Failed to load events"
                //e.printStackTrace()
                //Log.e("EventViewModel", "Error fetching events", e)
                _error.value = "Failed to load events: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}