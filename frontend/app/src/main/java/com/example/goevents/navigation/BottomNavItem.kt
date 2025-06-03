package com.example.goevents.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Events : BottomNavItem("events", Icons.Default.List, "Events")
    object Saved : BottomNavItem("saved", Icons.Default.Favorite, "Saved")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}