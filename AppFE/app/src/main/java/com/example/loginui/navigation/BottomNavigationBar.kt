package com.example.loginui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.loginui.Screen.Account
import com.example.loginui.Screen.Favorites
import com.example.loginui.Screen.HomeScreen
import com.example.loginui.Screen.Setting

import com.example.loginui.data.Destionations
import com.example.loginui.data.items



@Composable
fun BottomNavigationBar(bottomNavController: NavHostController) {

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate("Home_Screen")
                            }

                            1 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate("Favorites")
                            }

                            2 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate("Account")
                            }

                            3 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate("Setting")
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground

                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }

        }
    }
}

@Composable
fun MidScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController = bottomNavController)


        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding()
                .fillMaxSize()
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = "Home_Screen"
            ) {

                composable("Home_Screen") {
                    HomeScreen(navController = navController)
                }
                composable("Account") {
                    Account(navController = navController)
                }
                composable("Favorites") {
                    Favorites(navController = navController)
                }
                composable("Setting") {
                    Setting(navController = navController)
                }
            }
        }
    }
}


//@Composable
//fun BottomNavigationView(){
//    val navController = rememberNavController()
//
//
//        SetupNavGraph(navController = navController)
//
//}

//@Composable
//fun BottomBar(navController: NavHostController) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    items.forEachIndexed{index, item ->
//        NavigationBar {
//
//        }
//
//
//    }
//}

//@Composable
//fun BottomBar(
//    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
//) {
//    val screens = listOf(
//        Destionations.HomeScreen,
//        Destionations.Favorites,
//        Destionations.Account,
//        Destionations.Setting
//    )
//
//    NavigationBar(
//        modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        screens.forEach { screen ->
//
//            NavigationBarItem(
//                label = {
//                    Text(text = screen.title!!)
//                },
//                icon = {
//                    Icon(imageVector = screen.icon!!, contentDescription = "")
//                },
//                selected = currentRoute == screen.route,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    unselectedTextColor = Color.Gray, selectedTextColor = Color.Black
//                ),
//            )
//        }
//    }
//
//}

