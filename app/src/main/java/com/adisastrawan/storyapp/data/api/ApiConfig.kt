package com.adisastrawan.storyapp.data.api

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import com.adisastrawan.storyapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService():ApiService{
        val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }
//        val authInterceptor = Interceptor{chain->
//            val req = chain.request()
//            val requestHeader =req.newBuilder()
//                .addHeader("Authorization","Bearer $token")
//                .build()
//            chain.proceed(requestHeader)
//        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)

    }
}