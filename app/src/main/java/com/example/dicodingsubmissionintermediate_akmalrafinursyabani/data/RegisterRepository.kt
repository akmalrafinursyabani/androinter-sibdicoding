package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityRegisterBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.RegisterResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.LoginActivity
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.MainActivity
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository(
    private val name: String,
    private val email: String,
    private val password: String,
    private val activity: Activity? = null,
    private val binding: ActivityRegisterBinding? = null
) {

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            // jika error unable to create call adapter, jangan lupa add suspend/hapus method Call (retrofit)
            val response = APIConfig.getAPIService().register(name, email, password)
            val resultIntent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(resultIntent)
            activity?.finish()

            Toast.makeText(
                activity,
                "Register berhasil! Silahkan login untuk melanjutkan.",
                Toast.LENGTH_SHORT
            ).show()

            emit(Result.Success(response))

        } catch (e: Exception) {
            Log.d("RegisterRepository", "Register response: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
}