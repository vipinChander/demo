package com.example.demo.data.local

import androidx.room3.Dao
import androidx.room3.Insert

@Dao
interface UserDio {
   @Insert
    suspend fun InsertUserData(vararg Users: LoginData)
}