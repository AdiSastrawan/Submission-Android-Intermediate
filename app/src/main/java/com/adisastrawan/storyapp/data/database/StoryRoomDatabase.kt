package com.adisastrawan.storyapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StoryEntity::class],version=1, exportSchema = false)
abstract class StoryRoomDatabase : RoomDatabase(){
    abstract fun storyDao() : StoryDao
    companion object{
        @Volatile
        private var instance : StoryRoomDatabase? = null

        @JvmStatic
        fun getInstance(context : Context) : StoryRoomDatabase {
            if(instance == null){
                synchronized(StoryRoomDatabase::class.java){
                    instance = Room.databaseBuilder(context, StoryRoomDatabase::class.java,"story_database").build()

                }
            }
            return instance as StoryRoomDatabase
        }
    }
}