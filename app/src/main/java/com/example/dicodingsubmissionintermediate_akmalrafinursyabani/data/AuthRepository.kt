package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.clearSession
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.UploadStoryResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.LoginActivity

class AuthRepository(private val context: Context, private val activity: Activity) {
    fun logout(): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            clearSession(context)
            val intent = Intent(context, LoginActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
            Toast.makeText(context, "Berhasil logout", Toast.LENGTH_SHORT).show()
            emit(Result.Success("Berhasil logout"))
        } catch (e: Exception) {
            Log.d("AuthRepository", "Auth response: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }
}