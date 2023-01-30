package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.AddStoryRepository
import java.io.File
import kotlin.math.ln

class AddStoryViewModel(private val addStoryRepository: AddStoryRepository): ViewModel() {
    fun addStory(file: File, lat: String, lng: String) = addStoryRepository.uploadStory(
        file = file, lat = lat, lng = lng
    )
}