package com.adisastrawan.storyapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adisastrawan.storyapp.di.Injection
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.addstory.AddStoryViewModel
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.ui.auth.dataStore
import com.adisastrawan.storyapp.ui.auth.login.LoginViewModel
import com.adisastrawan.storyapp.ui.auth.register.RegisterViewModel
import com.adisastrawan.storyapp.ui.liststory.ListStoryViewModel

class ViewModelFactory private constructor(
    private val storyAppRepository: StoryAppRepository,
    private val authPreferences : AuthPreferences
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(storyAppRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(storyAppRepository) as T
        }
        else if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(authPreferences) as T
        }else if(modelClass.isAssignableFrom(ListStoryViewModel::class.java)){
            return ListStoryViewModel(storyAppRepository) as T
        }else if(modelClass.isAssignableFrom(AddStoryViewModel::class.java)){
            return AddStoryViewModel(storyAppRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    AuthPreferences.getInstance(context.dataStore)
                )
            }.also { instance = it }
        }
    }
}