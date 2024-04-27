package com.adisastrawan.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.adisastrawan.storyapp.data.api.ApiService
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.adisastrawan.storyapp.ui.auth.AuthPreferences

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(private val database:StoryRoomDatabase, private val apiService: ApiService,):
    RemoteMediator<Int, StoryEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryEntity>
    ): MediatorResult {
        val page = INITIAL_PAGE
//        try {
//
//            val response = apiService.getStories()
//        }catch (){
//
//        }
        return MediatorResult.Success(true)
    }

    companion object{
        const val INITIAL_PAGE = 1
    }


}