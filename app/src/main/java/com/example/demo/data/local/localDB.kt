package com.example.demo.data.local

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver

object localDB {
    private lateinit var db: AppDataBase
    fun startDbDataBAse(context: Context) {
        db = Room.databaseBuilder<AppDataBase>(
            context.applicationContext,
            AppDataBase::class.java,
            "User_DataBase",
        ).build()
    }

    fun getData(): AppDataBase {
        return db
    }
}