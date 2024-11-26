package com.example.wallet.presentation.app

import android.app.Application
import androidx.room.Room
import com.example.wallet.data.dataBase.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    lateinit var dataBase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}