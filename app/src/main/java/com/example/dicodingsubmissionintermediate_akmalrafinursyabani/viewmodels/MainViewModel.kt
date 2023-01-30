package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.StoryRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.di.Injection
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem

class MainViewModel(storyRepository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)
}

