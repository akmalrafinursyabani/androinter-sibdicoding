package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val id: String? = "",
    val name: String? = "",
    val lat: String? = "",
    val lon: String? = "",
    val photoStory: String? = "",
    val description: String? = "",
    val createdAt: String? = ""
): Parcelable
