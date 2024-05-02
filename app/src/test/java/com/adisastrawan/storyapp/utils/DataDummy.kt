package com.adisastrawan.storyapp.utils

import com.adisastrawan.storyapp.data.database.StoryEntity

object DataDummy {
    fun generateDummyStories():List<StoryEntity>{
        val stories = ArrayList<StoryEntity>()
        for(i in 0..100){
            val story = StoryEntity(
                id = i.toString(),
                imageUrl ="https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:924dcc7e3d877a61292899b4c4ca8f1820220124100739.jpeg",
                username = "username $i",
                description = "description $i"
            )
            stories.add(story)
        }
        return stories
    }
}