package com.adisastrawan.storyapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")

data class StoryEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id  : Int = 0,
    @ColumnInfo(name="username")
    var username : String,
    @ColumnInfo(name="imageUrl")
    var imageUrl : String,
    @ColumnInfo(name = "description")
    var description : String
)
