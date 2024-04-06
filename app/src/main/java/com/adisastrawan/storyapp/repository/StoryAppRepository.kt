package com.adisastrawan.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.adisastrawan.storyapp.data.api.ApiService
import com.adisastrawan.storyapp.data.api.response.LoginResponse
import com.adisastrawan.storyapp.data.api.response.RegisterResponse
import com.adisastrawan.storyapp.utils.Result
import retrofit2.HttpException

class StoryAppRepository(private val apiService: ApiService) {

    fun register(
        username: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val _response = MutableLiveData<Result<RegisterResponse>>()

            val response : LiveData<Result<RegisterResponse>> = _response
            _response.value = Result.Success(apiService.register(username, email, password))
            emitSource(response)
        } catch (e: Exception) {
            Log.e(TAG,e.message.toString())
            emit(Result.Error(e.message.toString()))
        }

    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val _response = MutableLiveData<Result<LoginResponse>>()

            val response : LiveData<Result<LoginResponse>> = _response
            _response.value = Result.Success(apiService.login(email, password))
            emitSource(response)
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        const val TAG = "StoryAppRepository"
        @Volatile
        private var INSTANCE: StoryAppRepository? = null
        fun getInstance(apiService: ApiService): StoryAppRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryAppRepository(apiService)
            }.also { INSTANCE = it }
        }
    }
}