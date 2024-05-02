package com.adisastrawan.storyapp.ui.addstory

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository
import java.io.File

class AddStoryViewModel(private val repository:StoryAppRepository): ViewModel() {
    fun postStory( file: File, description:String,lat:Double?,lon:Double?)=repository.postStory(file, description,lat,lon)
}