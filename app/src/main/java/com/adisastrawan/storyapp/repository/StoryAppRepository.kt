package com.adisastrawan.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.adisastrawan.storyapp.data.StoryPagingSource
import com.adisastrawan.storyapp.data.StoryRemoteMediator
import com.adisastrawan.storyapp.data.api.ApiService
import com.adisastrawan.storyapp.data.api.response.ListStoryItem
import com.adisastrawan.storyapp.data.api.response.LoginResponse
import com.adisastrawan.storyapp.data.api.response.RegisterResponse
import com.adisastrawan.storyapp.data.database.RemoteKeysDao
import com.adisastrawan.storyapp.data.database.StoryDao
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.utils.Result
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryAppRepository(private val apiService: ApiService,private val database: StoryRoomDatabase) {
    private val storyDao = database.storyDao()

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
            val loginResult = response.data.loginResult
            emit(response)
        }catch (e: HttpException) {
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
        }
    }

    fun getStories():LiveData<PagingData<StoryEntity>> {
        @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMediator(database, apiService),
                pagingSourceFactory = {
                    storyDao.getAllStory()
                }
            ).liveData
    }

    fun postStory( file: File,description:String):LiveData<Result<RegisterResponse>> = liveData{
        emit(Result.Loading)
        try {
            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val response = apiService.postStory(multipartBody,requestBody)
            emit(Result.Success(response))
        }catch (e:HttpException){
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG,errorMessage)
            emit(Result.Error(errorMessage))
        }

    }
    fun getStoriesWithLocation():LiveData<Result<List<ListStoryItem>>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
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
        fun getInstance(apiService: ApiService,database: StoryRoomDatabase): StoryAppRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryAppRepository(apiService, database)
            }.also { INSTANCE = it }
        }
    }
}