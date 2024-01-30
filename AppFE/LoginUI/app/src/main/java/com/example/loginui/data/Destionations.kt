package com.example.loginui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.ui.graphics.vector.ImageVector

data class Destionations(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    class Favorites {

    }
}

val items = listOf(
    Destionations(
        route = "Home_Screen",
        title = "Home",
        icon = Icons.Rounded.Home,

        ),

    Destionations(
        route = "Favorites_screen",
        title = "Favorites",
        icon = Icons.Rounded.Favorite
    ),

    Destionations(
        route = "Account_Screen",
        title = "Account",
        icon = Icons.Rounded.AccountCircle
    ),

    Destionations(
        route = "Setting_Screen",
        title = "Setting",
        icon = Icons.Rounded.Menu
    )
)
