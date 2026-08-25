package com.example.demo.data.remote

data class PexelsResponse(
    val page: Int,
    val per_page: Int,
    val photos: List<PexelsPhoto>,
    val total_results: Int
)

data class PexelsPhoto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val src: PexelsImageSource
)

data class PexelsImageSource(
    val original: String,
    val large: String,
    val medium: String,
    val small: String
)