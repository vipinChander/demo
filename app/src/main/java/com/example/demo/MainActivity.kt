package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.ElevatedButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.data.local.localDB
import com.example.demo.presenation.ui.UI.HomeScreen
import com.example.demo.presenation.ui.UI.signup
import com.example.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try{
            var db = localDB.startDbDataBAse(context = this)

        }catch (e: Exception){}
        setContent {
                DemoTheme {
                    AppNavigation()
                }
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
@Composable
fun LoginUI(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {


        var username = rememberSaveable { mutableStateOf("") }
        var password = remember { mutableStateOf("") }
        Text(text = "Login  Detail", style = TextStyle(fontSize = 20.sp, color = Color.Green))
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = username.value,
            onValueChange = {
                username.value = it
            },


            )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),

            value = password.value, onValueChange = {
                password.value = it
            })
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Login")
        }
        Spacer(modifier = Modifier.requiredSize(50.dp))
        TextButton(onClick = {
            navController.navigate("sign")
        }) {
            Text(" if don't have account then Sign in", style = TextStyle(fontSize = 12.sp))
        }



    }

}