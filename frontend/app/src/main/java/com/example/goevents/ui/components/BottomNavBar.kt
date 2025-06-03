package com.example.goevents.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goevents.navigation.Screen

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(Screen.EventList.route, Icons.Filled.Home, "Events"),
        BottomNavItem(Screen.SavedEvents.route, Icons.Default.Favorite, "Saved"),
        BottomNavItem(Screen.Profile.route, Icons.Default.Person, "Profile")
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.EventList.route)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}