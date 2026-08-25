package com.example.demo.data.remote


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/search")
    suspend fun searchImages(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 80
    ): PexelsResponse
}