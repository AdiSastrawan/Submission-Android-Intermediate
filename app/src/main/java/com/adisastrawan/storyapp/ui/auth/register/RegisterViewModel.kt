package com.adisastrawan.storyapp.ui.auth.register

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository

class RegisterViewModel(private val repository: StoryAppRepository) : ViewModel() {
    fun register(username:String,email:String,password:String)= repository.register(username, email, password)
}