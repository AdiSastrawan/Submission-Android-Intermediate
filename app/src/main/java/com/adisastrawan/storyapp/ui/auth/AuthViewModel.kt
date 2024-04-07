package com.adisastrawan.storyapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val authPreferences: AuthPreferences) : ViewModel() {
    fun getToken(): LiveData<String> = authPreferences.getToken().asLiveData()
    fun getUsername(): LiveData<String> = authPreferences.getUsername().asLiveData()
    fun getUserId(): LiveData<String> = authPreferences.getUserId().asLiveData()
    fun saveAuth(token:String,username:String,id:String){
        viewModelScope.launch {
            authPreferences.saveAuth(token,username,id)
        }
    }
}