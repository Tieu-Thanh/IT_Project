package com.example.loginui.API

import com.example.loginui.data.ModelResource
import com.example.loginui.data.authen.SignInRequest
import com.example.loginui.data.authen.SignInResponse
import com.example.loginui.data.authen.SignUpRequest
import com.example.loginui.data.authen.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path


interface AuthService {
        @POST("v1/accounts:signInWithPassword?key=AIzaSyBLwxPmvV-fDQEBzwHm30WjaOOtOH7vaY8")
        fun userLogin(@Body request: SignInRequest): Call<SignInResponse>

        @POST("v1/accounts:signUp?key=AIzaSyBLwxPmvV-fDQEBzwHm30WjaOOtOH7vaY8")
        fun userSignUp(@Body request: SignUpRequest): Call<SignUpResponse>
        @Multipart
        @POST("api/models/{model_id}/images")
        fun uploadImages(@Part images: List<MultipartBody.Part>,@Path("model_id") model_id:String): Call<ResponseBody>

        @POST("api/models")
        fun postModelInfo(@Body modelDetail: ModelResource): Call<ResponseBody>
}
