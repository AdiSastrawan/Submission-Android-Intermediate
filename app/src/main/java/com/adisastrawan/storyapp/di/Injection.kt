package com.adisastrawan.storyapp.di

import android.content.Context
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.ui.auth.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context):StoryAppRepository{
        val pref = AuthPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getAuth().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val database = StoryRoomDatabase.getInstance(context)

        return StoryAppRepository.getInstance(apiService,database)
    }
}