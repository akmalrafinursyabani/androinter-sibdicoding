package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.di

import android.app.Activity
import android.content.Context
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.*
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.database.StoryDatabase
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.*
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.google.android.gms.maps.GoogleMap

object Injection {
    fun provideRepository(context: Context, activity: Activity): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = APIConfig.getAPIService()
        return StoryRepository(database, apiService, context, activity)
    }

    fun provideLoginRepository(
        context: Activity,
        email: String,
        password: String,
        user: User,
        binding: ActivityLoginBinding
    ): LoginRepository {
        return LoginRepository(email, password, context, user, binding)
    }

    fun provideRegisterRepository(
        activity: Activity,
        name: String,
        email: String,
        password: String,
        binding: ActivityRegisterBinding
    ): RegisterRepository {
        return RegisterRepository(
            name = name,
            email = email,
            activity = activity,
            password = password,
            binding = binding
        )
    }

    fun provideDetailStoryRepository(
        activity: Activity,
        binding: ActivityDetailStoryBinding
    ): DetailStoryRepository {
        return DetailStoryRepository(
            activity = activity,
            binding = binding
        )
    }

    fun provideStoryLocationRepository(
        context: Context,
        googleMap: GoogleMap,
        activity: Activity
    ): StoriesLocationRepository {
        return StoriesLocationRepository(
            context = context,
            googleMap = googleMap,
            activity = activity
        )
    }

    fun provideAddStoryRepository(
        context: Context,
        binding: ActivityAddStoryBinding,
        activity: Activity
    ): AddStoryRepository {
        return AddStoryRepository(
            context = context,
            binding = binding,
            activity = activity
        )
    }

    fun provideAuthRepository(
        context: Context,
        activity: Activity
    ): AuthRepository {
        return AuthRepository(
            context = context,
            activity = activity
        )
    }
}