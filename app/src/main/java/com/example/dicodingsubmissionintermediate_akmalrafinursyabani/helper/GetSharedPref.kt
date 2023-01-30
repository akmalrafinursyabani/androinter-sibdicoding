package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper

import android.content.Context
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference

fun getSharedPref(context: Context): String {
    val userPreference = UserPreference(context = context)
    val user = userPreference.getSession()

    return user.token.toString()
}

fun clearSession(context: Context) {
    val userPreference = UserPreference(context = context)
    val user = userPreference.clearSession()
}