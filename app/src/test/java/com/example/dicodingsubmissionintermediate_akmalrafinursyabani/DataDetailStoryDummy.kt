package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResult
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.Story
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse

object DataDetailStoryDummy {
    fun initDataDetailStoryDummy(): StoryDetailResponse {
        return StoryDetailResponse(
            error = false,
            message = "success",
            story = Story(
                id = "story-3HafEO9--157GfNf",
                name = "acaaca",
                description = "bskdy",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1671515928479_5Co-DFVl.jpg",
                createdAt = "2022-12-20T05:58:48.481Z",
                lat = -0.9189189,
                lon = 100.44535
            )
        )
    }
}