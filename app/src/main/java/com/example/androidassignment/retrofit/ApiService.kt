package com.example.androidassignment.retrofit

import com.example.androidassignment.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("6HBE")
    fun fetchData(): Call<ApiResponse>
}
