package com.example.demo.data.local.repository

import com.example.demo.BuildConfig
import com.example.demo.data.remote.PexelsPhoto

class ImageRepository(
    private val api: com.example.demo.data.remote.PexelsApi
) {

    suspend fun searchImages(query: String): List<PexelsPhoto> {

        val response = api.searchImages(
            apiKey = BuildConfig.PEXELS_API_KEY,
            query = query
        )

        return response.photos
    }
}