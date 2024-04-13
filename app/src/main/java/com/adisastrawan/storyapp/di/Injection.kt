package com.adisastrawan.storyapp.di

import android.content.Context
import android.util.Log
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.data.api.response.LoginResult
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.ui.auth.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository():StoryAppRepository{
        val apiService = ApiConfig.getApiService()
        return StoryAppRepository.getInstance(apiService)
    }
}