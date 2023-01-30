package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItemWithLocation
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoriesWithLocationResponse

object DataStoriesLocationDummy {
    fun initDataStoriesLocationDummy(): StoriesWithLocationResponse {
        val storyLocationList = ArrayList<ListStoryItemWithLocation>()
        for (i in 0..5) {
            val story = ListStoryItemWithLocation(

                id = "story-_Ie8hiG2ow8K37tY",
                name = "wednesday",
                description = "anjaz kelaz",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1671552663112_ACV9JXxh.jpg",
                createdAt = "2022-12-20T16:11:03.113Z",
                lat = -0.9189189,
                lon = 100.42733
            )
            storyLocationList.add(story)
        }

        return StoriesWithLocationResponse(
            error = false,
            message = "Story Successfully fetched!",
            listStory = storyLocationList
        )
    }
}