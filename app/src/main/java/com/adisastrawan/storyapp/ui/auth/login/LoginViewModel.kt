package com.adisastrawan.storyapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryAppRepository):ViewModel() {
    fun login(email:String , password:String )=repository.login(email, password)

}