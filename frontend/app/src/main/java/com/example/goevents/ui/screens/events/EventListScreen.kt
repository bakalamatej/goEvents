package com.example.goevents.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goevents.ui.components.EventCard
import com.example.goevents.ui.components.EventDetailModal
import com.example.goevents.ui.components.FilterDialog
import com.google.accompanist.swiperefresh.*

@Composable
fun EventListScreen(
    onEventClick: (String) -> Unit,
    viewModel: EventViewModel = hiltViewModel()
) {
    var showFilterDialog by rememberSaveable { mutableStateOf(false) }
    var filterLocation by rememberSaveable { mutableStateOf("") }
    var filterEventType by rememberSaveable { mutableStateOf<String?>(null) }
    var filterDate by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedEvent by rememberSaveable { mutableStateOf<com.example.goevents.domain.model.Event?>(null) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val events by viewModel.events.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.filterEvents(title = it)
            },
            placeholder = { Text("Search by title") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    showFilterDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = "Filter Icon"
                    )
                }
            },
            singleLine = true
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.refreshEvents()
                searchQuery = ""
            },
            modifier = Modifier.weight(1f)
        ) {
            when {
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                events.isEmpty() && !isLoading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "No events available",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(events) { event ->
                            EventCard(
                                event = event,
                                onClick = { selectedEvent = event }
                            )
                        }
                    }
                }
            }
        }
        selectedEvent?.let { event ->
            EventDetailModal(
                event = event,
                onDismiss = { selectedEvent = null }
            )
        }
        if (showFilterDialog) {
            FilterDialog(
                location = filterLocation,
                onLocationChange = { filterLocation = it },
                eventType = filterEventType,
                onEventTypeChange = { filterEventType = it },
                date = filterDate,
                onDateChange = { filterDate = it },
                onDismiss = { showFilterDialog = false },
                onApply = { isoDate ->
                    viewModel.filterEvents(filterLocation, filterEventType, isoDate)
                    showFilterDialog = false
                }
            )
        }
    }
}