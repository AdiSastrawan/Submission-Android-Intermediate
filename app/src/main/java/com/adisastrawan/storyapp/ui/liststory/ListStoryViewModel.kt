package com.adisastrawan.storyapp.ui.liststory

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository

class ListStoryViewModel(private val repository: StoryAppRepository):ViewModel() {
    fun getStories()=repository.getStories()
}