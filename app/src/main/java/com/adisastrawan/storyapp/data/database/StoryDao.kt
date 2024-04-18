package com.adisastrawan.storyapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storyEntity: List<StoryEntity>)
    @Query("DELETE FROM story ")
    suspend fun deleteAll()
    @Query("SELECT * FROM story ")
    suspend fun getAllStory() : List<StoryEntity>
}