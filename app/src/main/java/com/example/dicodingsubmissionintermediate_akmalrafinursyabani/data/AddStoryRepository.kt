package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityAddStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.getSharedPref
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.imageSizeReducer
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.UploadStoryResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryRepository(
    private val context: Context,
    private val binding: ActivityAddStoryBinding,
    private val activity: Activity
) {

    fun uploadStory(
        file: File, lat: String, lng: String
    ): LiveData<Result<UploadStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            if (file != null) {
                if (binding.edtDesc.text.isNotEmpty()) {
                    val file = imageSizeReducer(file as File)

                    val getToken = getSharedPref(context = context)
                    validateToken(getToken, activity)

                    val desc =
                        binding.edtDesc.text.toString().toRequestBody("text/plain".toMediaType())
                    val reqImageFile = file.asRequestBody("images/jpeg".toMediaTypeOrNull())
                    val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo", file.name, reqImageFile
                    )
                    val useLocation = binding.locationToggle.isChecked
                    val noLat = "null".toRequestBody("text/plain".toMediaType())
                    val noLgn = "null".toRequestBody("text/plain".toMediaType())

                    val response = if (!useLocation) {
                        APIConfig.getAPIService().uploadStory(
                            token = "Bearer $getToken",
                            file = imageMultiPart,
                            description = desc
                        )
                    } else {
                        APIConfig.getAPIService().uploadStory(
                            token = "Bearer $getToken",
                            file = imageMultiPart,
                            description = desc,
                            lon = lng.toRequestBody("text/plain".toMediaType()),
                            lat = lat.toRequestBody("text/plain".toMediaType()),
                        )
                    }
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()

                    Toast.makeText(
                        activity,
                        "Story berhasil diupload!",
                        Toast.LENGTH_SHORT
                    ).show()
                    emit(Result.Success(response))
                } else {
                    binding.edtDesc.error = "Deskripsi harus diisi!"
                }
            } else {
                Toast.makeText(
                    activity,
                    "Silahkan upload gambar atau ambil foto terlebih dahulu.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.d("AddStoryRepository", "AddStory response: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }

}