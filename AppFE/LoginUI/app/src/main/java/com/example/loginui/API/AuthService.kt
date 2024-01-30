package com.example.loginui.API

import com.example.loginui.data.SignInRequest
import com.example.loginui.data.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response


interface AuthService {
        @POST("v1/accounts:signInWithPassword?key=AIzaSyBLwxPmvV-fDQEBzwHm30WjaOOtOH7vaY8")
        fun userLogin(@Body request: SignInRequest): Call<SignInResponse>
}