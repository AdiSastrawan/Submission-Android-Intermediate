package com.adisastrawan.storyapp.ui.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth")

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val USERNAME = stringPreferencesKey("username")
    private val USER_ID = stringPreferencesKey("user_id")
    fun getToken(): Flow<String> = dataStore.data.map {
        it[TOKEN_KEY] ?: ""
    }

    fun getUsername(): Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    fun getUserId(): Flow<String> = dataStore.data.map {
        it[USER_ID] ?: ""
    }

    suspend fun saveAuth(token: String, username: String, id: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
            it[USERNAME] = username
            it[USER_ID] = id
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}