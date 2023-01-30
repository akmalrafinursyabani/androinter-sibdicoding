package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.getSharedPref
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse

class DetailStoryRepository(
    private val activity: Activity,
    private val binding: ActivityDetailStoryBinding
) {
    fun getStoryDetail(
        idStory: String,
        viewBinding: ActivityDetailStoryBinding? = null,
        context: Context? = null
    ): LiveData<Result<StoryDetailResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val getToken = getSharedPref(context = context!!)
                validateToken(getToken, activity)
                val response =
                    APIConfig.getAPIService()
                        .getStoryDetail(token = "Bearer $getToken", id = idStory)

                val name = response.story.name
                val desc = response.story.description
                val photoUrl = response.story.photoUrl

                binding.uploaderName.text = name
                binding.uploaderDescription.text = desc
                binding.storyImage.let {
                    if (context != null) {
                        Glide.with(context).load(photoUrl)
                            .into(it)
                    }
                }
                emit(Result.Success(response))

            } catch (e: Exception) {
                Log.d("DetailStoryRepository", "Detail Story response: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
}