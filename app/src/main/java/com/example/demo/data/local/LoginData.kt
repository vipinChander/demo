package com.example.demo.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey


    @Entity(tableName = "login_data")
    data class LoginData(
        @PrimaryKey
        val id :Int,
        @ColumnInfo("user_email") val email :String,
       @ColumnInfo("user_password") val password: String

    )
