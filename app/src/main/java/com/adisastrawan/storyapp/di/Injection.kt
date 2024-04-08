package com.adisastrawan.storyapp.di

import android.content.Context
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.ui.auth.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context):StoryAppRepository{
        val preferences = AuthPreferences.getInstance(context.dataStore)
        val auth = runBlocking {
            preferences.getAuth().first()
        }

        val apiService = ApiConfig.getApiService(auth.token)
        return StoryAppRepository.getInstance(apiService)
    }
}