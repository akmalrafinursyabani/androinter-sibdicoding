package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.StoriesLocationRepository

class StoriesLocationViewModel(private val storiesLocationRepository: StoriesLocationRepository): ViewModel() {
    fun storiesLocation() =
        storiesLocationRepository.storiesMarkerBounds()
}