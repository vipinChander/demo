package com.example.demo.data.local

data class GoogleImageResponse(
    val items: List<ImageItem>?
)


data class ImageItem(
    val id: Long,
    val imageUrl: String,
    val photographer: String,
    val photoUrl: String,
    val description: String?
)

data class ImageInfo(
    val thumbnailLink: String?,
    val contextLink: String?
)