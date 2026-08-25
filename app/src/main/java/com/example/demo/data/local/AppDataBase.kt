package com.example.demo.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase


@Database([LoginData::class], version = 1)
abstract  class AppDataBase : RoomDatabase() {

}