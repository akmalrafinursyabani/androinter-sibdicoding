package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.Story
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.UploadStoryResponse

object DataAddStoryDummy {
    fun initDataAddStoryDummy(): UploadStoryResponse {
        return UploadStoryResponse(
            error = false,
            message = "success",
        )
    }
}