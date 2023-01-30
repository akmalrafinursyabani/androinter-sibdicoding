package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import android.content.Intent
import android.os.IBinder
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.repository.StoriesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Service: android.app.Service() {

    @Inject
    lateinit var storiesRepository: StoriesRepository

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}