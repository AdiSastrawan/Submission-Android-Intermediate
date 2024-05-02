package com.adisastrawan.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.adisastrawan.storyapp.data.StoryRemoteMediator
import com.adisastrawan.storyapp.data.api.ApiService
import com.adisastrawan.storyapp.data.api.response.ListStoryItem
import com.adisastrawan.storyapp.data.api.response.LoginResponse
import com.adisastrawan.storyapp.data.api.response.RegisterResponse
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.ui.auth.AuthPreferences
import com.adisastrawan.storyapp.utils.Result
import com.adisastrawan.storyapp.utils.wrapEspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryAppRepository(
    private val apiService: ApiService,
    private val database: StoryRoomDatabase,
    private val dataPreferences: AuthPreferences
) {
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
            Log.e(TAG, errorMessage)
            emit(Result.Error(errorMessage))
        }

    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = Result.Success(apiService.login(email, password))
                val loginResult = response.data.loginResult
                dataPreferences.saveAuth(loginResult.token, loginResult.name, loginResult.userId)
                emit(response)
            } catch (e: HttpException) {
                val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
                Log.e(TAG, errorMessage)
                emit(Result.Error(errorMessage))
            }
        }
    }

    fun getStories(): LiveData<PagingData<StoryEntity>> {
        wrapEspressoIdlingResource {
            val token: String = runBlocking { dataPreferences.getAuth().first().token }

            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMediator(database, apiService, "Bearer $token"),
                pagingSourceFactory = {
                    storyDao.getAllStory()
                }
            ).liveData
        }
    }

    fun postStory(
        file: File,
        description: String,
        lat: Double?,
        lon: Double?
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        val token: String = runBlocking { dataPreferences.getAuth().first().token }
        try {
            val requestDesciption = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val requestLat = lat?.toString()?.toRequestBody("text/plain".toMediaType())
            val requestLon = lon?.toString()?.toRequestBody("text/plain".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val response = apiService.postStory(
                multipartBody,
                requestDesciption,
                requestLat,
                requestLon,
                "Bearer $token"
            )
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG, errorMessage)
            emit(Result.Error(errorMessage))
        }


    }

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        val token: String = runBlocking { dataPreferences.getAuth().first().token }
        try {
            val response = apiService.getStoriesWithLocation(token = "Bearer $token")
            val responseResult = response.listStory
            emit(Result.Success(responseResult))
        } catch (e: HttpException) {
            val errorMessage = parseJsonToErrorMessage(e.response()?.errorBody()?.string())
            Log.e(TAG, errorMessage)
            emit(Result.Error(errorMessage))
        }


    }

    private fun parseJsonToErrorMessage(jsonInString: String?): String {
        val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
        return errorBody.message
    }


    companion object {
        const val TAG = "StoryAppRepository"

        @Volatile
        private var INSTANCE: StoryAppRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: StoryRoomDatabase,
            dataPreferences: AuthPreferences
        ): StoryAppRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryAppRepository(apiService, database, dataPreferences)
            }.also { INSTANCE = it }
        }
    }
}