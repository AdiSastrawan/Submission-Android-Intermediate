package com.adisastrawan.storyapp.ui.addstory

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository
import java.io.File

class AddStoryViewModel(private val repository:StoryAppRepository): ViewModel() {
    fun postStory(token:String, file: File, description:String)=repository.postStory(token,file, description)
}