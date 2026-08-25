package com.example.demo.data.local.repository

import com.example.demo.data.remote.PexelsResponse
import retrofit2.http.GET
import retrofit2.http.Query




import retrofit2.http.Header

interface  GoogleImageApi {

    @GET("v1/search")
    suspend fun searchPhotos(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 80
    ): PexelsResponse
}