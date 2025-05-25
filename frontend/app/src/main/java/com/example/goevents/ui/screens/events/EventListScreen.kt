package com.example.goevents.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goevents.navigation.Screen
import com.example.goevents.ui.components.EventCard
import com.google.accompanist.swiperefresh.*

@Composable
fun EventListScreen(
    onEventClick: (String) -> Unit,
    viewModel: EventViewModel = hiltViewModel()
) {
    val events by viewModel.events.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    var selectedEvent by remember { mutableStateOf<com.example.goevents.domain.model.Event?>(null) }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refreshEvents() }
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
}