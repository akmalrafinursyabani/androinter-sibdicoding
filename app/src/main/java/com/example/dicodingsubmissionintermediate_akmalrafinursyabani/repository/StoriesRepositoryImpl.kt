package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.repository

import android.app.Application
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service.APIService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val appContext: Application
) : StoriesRepository {
    init {
        runBlocking {
            getStories()
        }
    }

    override suspend fun getStories() {
        println("GET STORIEESSSS!!!! :D")
    }

    override suspend fun testingReturn(): String {
        return "KEPANGGIL JUGA DAGGER HILT :D"
    }
}