package com.example.goevents.navigation

sealed class Screen(val route: String) {
    object EventList : Screen("events")
    object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: String) = "event_detail/$eventId"
    }
    object SavedEvents : Screen("saved")
    object Profile : Screen("profile")
    object AddEvent : Screen("add_event")
    object Login : Screen("login")
    object Register: Screen("register")
}