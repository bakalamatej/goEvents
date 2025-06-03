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
import com.example.goevents.ui.MainScreen
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
            GoEventsTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun App() {
    GoEventsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}