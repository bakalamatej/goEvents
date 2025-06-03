package com.example.goevents.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goevents.ui.screens.events.EventListScreen
import com.example.goevents.ui.screens.profile.ProfileScreen
import com.example.goevents.ui.screens.saved.SavedEventsListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.EventList.route,
        modifier = modifier
    ) {
        composable(Screen.EventList.route) {
            EventListScreen(
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                }
            )
        }

        composable(Screen.SavedEvents.route) {
            SavedEventsListScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}