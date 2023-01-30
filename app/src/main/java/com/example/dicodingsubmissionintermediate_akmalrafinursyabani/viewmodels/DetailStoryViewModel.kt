package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.DetailStoryRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.Story
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.DetailStoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(private val detailStoryRepository: DetailStoryRepository) : ViewModel() {
    fun getStoryDetail(id: String, context: Context? = null) =
        detailStoryRepository.getStoryDetail(idStory = id, context = context)
}