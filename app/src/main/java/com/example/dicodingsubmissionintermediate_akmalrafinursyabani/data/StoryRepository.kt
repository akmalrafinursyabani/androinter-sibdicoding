package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.database.StoryDatabase
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.getSharedPref
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service.APIService

class StoryRepository(
    private val storyDatabase: StoryDatabase? = null,
    private val apiService: APIService? = null,
    private val context: Context? = null,
    private val activity: Activity? = null
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        val getToken = getSharedPref(context = context!!)
        validateToken(getToken, activity!!)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(
                token = getToken,
                storyDatabase = storyDatabase!!,
                apiService = apiService!!
            ),
            pagingSourceFactory = {
//                StoryPagingSource(apiService, token)
                storyDatabase.storyDao().getAllStoryAvailable()
            }
        ).liveData
    }
}