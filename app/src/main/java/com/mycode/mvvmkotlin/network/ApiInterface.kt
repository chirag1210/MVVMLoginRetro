package com.mycode.mvvmkotlin.network


import com.mycode.mvvmkotlin.model.LoginResponse
import com.mycode.mvvmkotlin.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("api/login")
    fun doLogin(@Body user: User): Call<LoginResponse>
}