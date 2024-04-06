package com.adisastrawan.storyapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adisastrawan.storyapp.di.Injection
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.ui.auth.login.LoginViewModel
import com.adisastrawan.storyapp.ui.auth.register.RegisterViewModel

class ViewModelFactory private constructor(private val storyAppRepository: StoryAppRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(storyAppRepository) as T
        }else if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(storyAppRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null
        fun getInstance(context: Context) : ViewModelFactory{
            return instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
        }
    }
}