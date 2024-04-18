package com.adisastrawan.storyapp.di

import android.content.Context
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.repository.StoryAppRepository

object Injection {
    fun provideRepository(context: Context):StoryAppRepository{
        val apiService = ApiConfig.getApiService()
        val database = StoryRoomDatabase.getInstance(context)
        val userDao = database.storyDao()
        return StoryAppRepository.getInstance(apiService,userDao)
    }
}