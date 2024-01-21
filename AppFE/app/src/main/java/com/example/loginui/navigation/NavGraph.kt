package com.example.loginui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginui.HomeScreen
import com.example.loginui.Login.SignIn
import com.example.loginui.Login.SignUp

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "SignIn",
    ){
        composable("SignIn"){
            SignIn(navController = navController)
        }
        composable("SignUp"){
            SignUp(navController = navController)
        }
        composable("HomeScreen"){
            HomeScreen(navController = navController)
        }
    }
}


