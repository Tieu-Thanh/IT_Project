package com.example.loginui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginui.FunctionDetail.Co_function
import com.example.loginui.FunctionDetail.DM_function
import com.example.loginui.Screen.AnimatedSplash

import com.example.loginui.Screen.HomeScreen
import com.example.loginui.Screen.ListObject
import com.example.loginui.Screen.ModelInfo
import com.example.loginui.Screen.SignIn
import com.example.loginui.Screen.SignUp
import com.example.loginui.SubScreen.About_us
import com.example.loginui.SubScreen.Contact_us
import com.example.loginui.SubScreen.News

var user: String = ""
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "AnimatedSplash",
    ) {
        composable("AnimatedSplash") {
            AnimatedSplash(navController = navController)
        }
        composable("SignIn") {
            SignIn(navController = navController)
        }
        composable("SignUp") {
            SignUp(navController = navController)
        }

        composable("News") {
            News(navController = navController)
        }
        composable("Contact us") {
            Contact_us(navController = navController)
        }
        composable("About us") {
            About_us(navController = navController)
        }
        composable("HomeScreen") {
            HomeScreen(navController = navController)
        }
        composable("C0_function") {
            Co_function(navController = navController)
        }
        composable("DM_function") {
            DM_function(navController = navController)
        }
        composable("ListObject"){
            ListObject(navController = navController)
        }
        composable("ModelInfo"){
            ModelInfo(navController = navController)
        }
    }
}

object Graph{
    const val ROOT = "nav_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}



