package com.example.demo.presenation.ui.UI

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.demo.LoginUI

import com.example.demo.data.local.repository.ImageRepository
import com.example.demo.data.local.repository.RetrofitClient
import com.example.demo.data.remote.PexelsPhoto
import com.example.demo.presenation.ui.State.homeScreenView
import com.google.firebase.messaging.FirebaseMessaging.getInstance


class HomeScreen : ComponentActivity() {

    private val repository by lazy {
        ImageRepository(
            RetrofitClient.apiService
        )
    }

    private val viewModel: homeScreenView by viewModels {

        object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {

                return homeScreenView(repository) as T
            }
        }
    }
    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("HomeScreen", "HomeScreen started")
        requestNotificationPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "default_channel",
                "General Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "App notifications"
            }

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(channel)
        }

        getInstance()
            .token
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Log.d("FCM", "Token: ${task.result}")
                }
            }

        setContent {
            Homescreen(viewModel)
        }
    }

    @Composable
    fun Homescreen(viewModel: homeScreenView) {

        val images by viewModel.images.collectAsState()
        val loading by viewModel.loading.collectAsState()
        val error by viewModel.error.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Search Image",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            val inputvalue = remember {
                mutableStateOf("")
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.6f),
                    value = inputvalue.value,
                    onValueChange = {
                        inputvalue.value = it
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(Modifier.width(20.dp))
                Button(
                    onClick = {
                        viewModel.searchImage(inputvalue.value)
                    }
                ) {
                    Text("Search Image")
                }
            }

            if (loading) {
                Text("Loading...")
            }else{
                Spacer(modifier = Modifier.height(20.dp))
                ImageGrid(images = images)
            }



            error?.let {
                Text(
                    text = "Error: $it"
                )
            }
        }
    }

    @Composable
    fun ImageGrid(
        images: List<PexelsPhoto>,

        ) {
        val context = LocalContext.current

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()

        ) {
            items(images) { imageUrl ->

                AsyncImage(
                    model = imageUrl.src.original,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, DetailActivity::class.java)

                            intent.putExtra("imageUrl", imageUrl.src.original)

                            context.startActivity(intent)
                        },
                )
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "login"

        ) {
            composable("login") {
                LoginUI(navController)
            }
            composable("sign") {
                HomeScreen()
            }
        }
    }
}