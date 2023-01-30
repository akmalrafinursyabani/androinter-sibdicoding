package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.repository

interface StoriesRepository {
    suspend fun getStories()

    suspend fun testingReturn() : String
}