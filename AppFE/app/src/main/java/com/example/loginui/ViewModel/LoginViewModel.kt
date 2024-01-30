package com.example.loginui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.loginui.API.AuthService
import com.example.loginui.data.SignInRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val authService: AuthService) {

//     fun signIn(email: String, password: String, navController: NavController) {
//        val request = SignInRequest(email, password)
//        val call = authService.userLogin(request)
//
//        if(call.isSuccessful){
//            CoroutineScope(Dispatchers.Main).run{
//                navController.navigate("HomeScreen")
//            }
//        }else{
//            val errorMessage = "Invalid credentials. Please try again."
//        }
//    }

}