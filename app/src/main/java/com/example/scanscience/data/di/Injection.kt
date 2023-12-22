package com.example.scanscience.data.di

import android.content.Context
import com.example.scanscience.data.api.ApiConfig
import com.example.scanscience.data.Repository
import com.example.scanscience.utils.UserPreferences
import com.example.scanscience.utils.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService, pref)
    }
}