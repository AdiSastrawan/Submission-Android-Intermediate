package com.adisastrawan.storyapp.di

import android.content.Context
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.ui.auth.dataStore

object Injection {
    fun provideRepository(context: Context):StoryAppRepository{
        val pref = AuthPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val database = StoryRoomDatabase.getInstance(context)

        return StoryAppRepository.getInstance(apiService,database,pref)
    }
}