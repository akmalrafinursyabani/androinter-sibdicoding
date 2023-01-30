package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.GetStoriesResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem

object DataStoryDummy {
    fun generateStoryDummies(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                id = "story-Hw7TH3OgoopYY34a",
                name = "Michael Schumacher",
                description = "test",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1671499922294_5go8JC4Y.jpg",
                createdAt = "2022-12-20T01:32:02.297Z",
                lat = "-0.9171613",
                lon = "100.4595018"
            )
            items.add(quote)
        }
        return items
    }
}