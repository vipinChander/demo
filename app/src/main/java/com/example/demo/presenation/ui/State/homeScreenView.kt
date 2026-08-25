package com.example.demo.presenation.ui.State

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.data.local.repository.ImageRepository
import com.example.demo.data.remote.PexelsPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class homeScreenView(
    private val repository: ImageRepository
) : ViewModel() {

    private val _images = MutableStateFlow<List<PexelsPhoto>>(emptyList())
    val images: StateFlow<List<PexelsPhoto>> = _images

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchImage(query: String) {

        viewModelScope.launch {

            try {

                _loading.value = true
                _error.value = null

                val result = repository.searchImages(query)
          Log.d("homeScreen","${result[0].src} ${result[0].url}")
                _images.value = result

            } catch (e: Exception) {

                e.printStackTrace()

                _error.value = e.message

            } finally {

                _loading.value = false
            }
        }
    }
}