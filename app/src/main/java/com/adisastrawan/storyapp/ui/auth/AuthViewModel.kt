package com.adisastrawan.storyapp.ui.auth

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adisastrawan.storyapp.data.api.response.LoginResult
import kotlinx.coroutines.launch

class AuthViewModel(private val authPreferences: AuthPreferences) : ViewModel() {
    fun getAuth(): LiveData<LoginResult> = authPreferences.getAuth().asLiveData()

    fun saveAuth(token:String,username:String,id:String){
        viewModelScope.launch {
            authPreferences.saveAuth(token,username,id)
        }
    }
}