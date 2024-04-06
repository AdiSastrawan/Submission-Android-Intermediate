package com.adisastrawan.storyapp.di

import android.content.Context
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.repository.StoryAppRepository

object Injection {
    fun provideRepository():StoryAppRepository{
        val apiService = ApiConfig.getApiService()
        return StoryAppRepository.getInstance(apiService)
    }
}