package com.example.wallet.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class AuthUtils(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setLogeIn(isLogedIn: Boolean){
        sharedPreferences.edit().putBoolean("isLogedIn",isLogedIn).apply()
    }

    fun isLogedIn():Boolean{
        return sharedPreferences.getBoolean("isLogedIn",false)
    }

}