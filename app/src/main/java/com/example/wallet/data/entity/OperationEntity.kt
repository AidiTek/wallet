package com.example.wallet.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "operations")
data class OperationEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val accountID: Int,
    val category: String,
    val amount: Int,
    val percentage: String,
    val isIncome: Boolean,
    val iconeResId : Int,
    val description : String? = null,
    val date : Long,
    val isAccount:Boolean = false
)