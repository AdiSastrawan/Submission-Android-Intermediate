package com.adisastrawan.storyapp.ui.liststory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.repository.StoryAppRepository

class ListStoryViewModel(private val repository: StoryAppRepository):ViewModel() {
    fun getStories() : LiveData<PagingData<StoryEntity>> = repository.getStories().cachedIn(viewModelScope)
}