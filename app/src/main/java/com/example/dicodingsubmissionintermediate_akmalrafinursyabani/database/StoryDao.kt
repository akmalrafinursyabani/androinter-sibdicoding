package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<ListStoryItem>)

    @Query("SELECT * FROM listStory")
    fun getAllStoryAvailable(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM listStory")
    suspend fun deleteAll()
}