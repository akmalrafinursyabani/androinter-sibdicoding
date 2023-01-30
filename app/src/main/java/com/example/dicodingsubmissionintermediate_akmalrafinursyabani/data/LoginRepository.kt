package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityLoginBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service.APIService
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.LoginActivity
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.MainActivity
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(
    private val email: String,
    private val password: String,
    private val context: Activity,
    private val user: User,
    private val binding: ActivityLoginBinding
) {

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        binding.loginProgressbar.visibility = View.VISIBLE
        try {
            val response = APIConfig.getAPIService().login(email, password)
            saveUserToPref(
                response.loginResult.userId,
                response.loginResult.name,
                email,
                response.loginResult.token,
                context
            )
            val resultIntent = Intent(context, MainActivity::class.java)
            resultIntent.putExtra(LoginActivity.EXTRA_RESULT, user)
            context.setResult(LoginActivity.RESULT_CODE, resultIntent)
            context.startActivity(resultIntent)
            context.finish()

            emit(Result.Success(response))


        } catch (e: Exception) {
            Log.d("LoginRepository", "Login response: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }


    private fun saveUserToPref(
        id: String,
        name: String,
        email: String,
        token: String,
        context: Context
    ) {
        val pref = UserPreference(context).apply {
            user.id = id
            user.name = name
            user.email = email
            user.token = token
        }

        user.id = id
        user.name = name
        user.email = email
        user.token = token
        pref.setSession(user)

        Toast.makeText(
            context,
            "Selamat datang. $name!", Toast.LENGTH_SHORT
        ).show()

    }
}