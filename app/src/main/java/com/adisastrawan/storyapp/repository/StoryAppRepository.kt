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
import com.google.gson.Gson
import retrofit2.HttpException

class StoryAppRepository(private val apiService: ApiService) {

    fun register(
        username: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = Result.Success(apiService.register(username, email, password))
            emit(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
        }

    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = Result.Success(apiService.login(email, password))
            emit(response)
        }catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
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