package com.example.goevents

import android.os.Bundle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.goevents.domain.model.Event
import com.example.goevents.domain.model.EventType
import com.example.goevents.navigation.NavGraph
import com.example.goevents.ui.components.EventCard
import com.example.goevents.ui.screens.events.EventListScreen
import com.example.goevents.ui.screens.events.EventViewModel
import com.example.goevents.ui.theme.GoEventsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }}

@Composable
fun App() {
    GoEventsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}

/*
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: EventViewModel = hiltViewModel()
            EventListScreen(
                viewModel = viewModel,
                onEventClick = { eventId ->
                    // TODO: Handle navigation to detail screen here
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewEventCard() {
    val sampleEvent = Event(
        id = "1",
        title = "Sample Event Title",
        description = "This is a sample event description.",
        startDate = Date(),
        endDate = Date(),
        location = "Sample Location",
        eventType = EventType.CONCERT,
        imageUrl = "https://via.placeholder.com/300",
        link = null,
        createdAt = Instant.now()
    )

    EventCard(
        event = sampleEvent,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEventListScreen() {
    val sampleEvents = listOf(
        Event(
            id = "1",
            title = "Sample Event 1",
            description = "Description for event 1",
            startDate = Date(),
            endDate = Date(),
            location = "Location 1",
            eventType = EventType.CONCERT,
            imageUrl = "https://via.placeholder.com/300",
            link = null,
            createdAt = Instant.now()
        ),
        Event(
            id = "2",
            title = "Sample Event 2",
            description = "Description for event 2",
            startDate = Date(),
            endDate = Date(),
            location = "Location 2",
            eventType = EventType.SPORTS,
            imageUrl = "https://via.placeholder.com/300",
            link = null,
            createdAt = Instant.now()
        ),
        Event(
            id = "3",
            title = "Sample Event 3",
            description = "Description for event 3",
            startDate = Date(),
            endDate = Date(),
            location = "Location 3",
            eventType = EventType.SPORTS,
            imageUrl = "https://via.placeholder.com/300",
            link = null,
            createdAt = Instant.now()
        ),
        Event(
            id = "4",
            title = "Sample Event 4",
            description = "Description for event 4",
            startDate = Date(),
            endDate = Date(),
            location = "Location 4",
            eventType = EventType.SPORTS,
            imageUrl = "https://via.placeholder.com/300",
            link = null,
            createdAt = Instant.now()
        )
    )

    EventListScreenPreviewContent(
        events = sampleEvents,
        isLoading = false,
        error = null,
        onEventClick = {},
        onRefresh = {}
    )
}

@Composable
fun EventListScreenPreviewContent(
    events: List<Event>,
    isLoading: Boolean,
    error: String?,
    onEventClick: (String) -> Unit,
    onRefresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        when {
            error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = error,
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
                        EventCard(event = event, onClick = { onEventClick(event.id) })
                    }
                }
            }
        }
    }
}*/