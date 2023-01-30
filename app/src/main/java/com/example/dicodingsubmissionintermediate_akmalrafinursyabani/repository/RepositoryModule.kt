package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.repository

import androidx.camera.core.CameraInfo.ImplementationType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStoriesRepository(
        storiesRepositoryImpl: StoriesRepositoryImpl
    ): StoriesRepository
}