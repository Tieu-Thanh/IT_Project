package com.example.loginui.SubScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun About_us(navController: NavHostController){
    Text(text = "Your model")
    Button(onClick = { navController.navigate("Check model") }) {
        Text(text = "Your model")
    }
}