package com.example.wallet.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet.data.dao.OperationDao
import com.example.wallet.data.entity.OperationEntity



@Database(entities = [OperationEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun operationDao(): OperationDao
}
