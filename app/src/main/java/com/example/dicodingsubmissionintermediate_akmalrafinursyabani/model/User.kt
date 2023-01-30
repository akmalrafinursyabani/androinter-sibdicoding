package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var token: String? = null
) : Parcelable