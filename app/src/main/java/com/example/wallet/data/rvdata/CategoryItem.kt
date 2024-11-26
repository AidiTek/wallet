package com.example.wallet.data.rvdata

import android.media.VolumeShaper.Operation
import com.example.wallet.data.entity.OperationEntity

data class CategoryItem(
    val iconeResId: Int,
    val title: String,
    val persentage:String,
    val amount : String,
    val operation :OperationEntity
)

