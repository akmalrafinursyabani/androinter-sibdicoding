package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper

import android.app.Activity
import android.content.Intent
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.LoginActivity

fun validateToken(token: String, activity: Activity) {
    if (token.isEmpty()) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}
