package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories?page=1&size=50&location=0")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<GetStoriesResponse>

    @GET("stories")
    suspend fun getStoriesWithQuery(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): GetStoriesResponse


    @GET("stories?location=1")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token: String
    ): StoriesWithLocationResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null
    ): UploadStoryResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): StoryDetailResponse
}