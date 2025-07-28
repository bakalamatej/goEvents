package com.example.goevents.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goevents.ui.screens.auth.AuthScreen
import com.example.goevents.ui.screens.auth.AuthViewModel
import com.example.goevents.ui.screens.auth.RegisterScreen
import com.example.goevents.ui.screens.events.AddEventScreen
import com.example.goevents.ui.screens.events.EventListScreen
import com.example.goevents.ui.screens.profile.ProfileScreen
import com.example.goevents.ui.screens.saved.SavedEventsListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.EventList.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            AuthScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

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
            val viewModel: AuthViewModel = hiltViewModel()
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.checkLoginStatus()
            }

            if (isLoggedIn) {
                ProfileScreen(navController = navController)
            } else {
                AuthScreen(navController = navController)
            }
        }


        composable(Screen.AddEvent.route) {
            AddEventScreen(navController = navController)
        }
    }
}