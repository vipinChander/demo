package com.example.demo.presenation.ui


import com.example.demo.data.local.ImageItem

data class ImageUiState(
    val isLoading: Boolean = false,
    val images: List<ImageItem> = emptyList(),
    val error: String? = null
)