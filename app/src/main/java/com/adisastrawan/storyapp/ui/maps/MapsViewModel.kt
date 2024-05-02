package com.adisastrawan.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository

class MapsViewModel(private val repository: StoryAppRepository) : ViewModel() {
    fun getStoriesWithLocation() = repository.getStoriesWithLocation()
}