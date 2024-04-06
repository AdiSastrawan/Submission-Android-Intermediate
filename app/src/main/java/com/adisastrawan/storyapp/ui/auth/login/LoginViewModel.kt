package com.adisastrawan.storyapp.ui.auth.login

import androidx.lifecycle.ViewModel
import com.adisastrawan.storyapp.repository.StoryAppRepository

class LoginViewModel(private val repository: StoryAppRepository):ViewModel() {
    fun login(email:String , password:String )=repository.login(email, password)
}