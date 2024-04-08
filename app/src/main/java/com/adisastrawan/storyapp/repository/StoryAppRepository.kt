package com.adisastrawan.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.data.api.ApiService
import com.adisastrawan.storyapp.data.api.response.ListStoryItem
import com.adisastrawan.storyapp.data.api.response.ListStoryResponse
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
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
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
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
        }
    }

    fun getStories():LiveData<Result<List<ListStoryItem>>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getStories()
            val responseResult = response.listStory
            emit(Result.Success(responseResult))
        }catch (e:HttpException){
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
        }

    }

    private fun parseJsonToErrorMessage(jsonInString:String?):String{
        val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
        return errorBody.message
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