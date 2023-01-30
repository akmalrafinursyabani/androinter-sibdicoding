package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service.APIService
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference

class StoryPagingSource(private val apiService: APIService, private val token: String) :
    PagingSource<Int, ListStoryItem>() {


    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_INDEX
            val responseData =
                apiService.getStoriesWithQuery("Bearer $token", position, params.loadSize).listStory

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.d("SPS KT", exception.message.toString())
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_INDEX = 1
    }
}