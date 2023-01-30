package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.AuthRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityAddStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityLoginBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityRegisterBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.di.Injection
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.google.android.gms.maps.GoogleMap

class ViewModelFactory(
    private val context: Context? = null,
    private val activity: Activity? = null,
    private val token: String? = null,
    private val name: String? = null,
    private val email: String? = null,
    private val password: String? = null,
    private val loginBinding: ActivityLoginBinding? = null,
    private val registerBinding: ActivityRegisterBinding? = null,
    private val detailStoryBinding: ActivityDetailStoryBinding? = null,
    private val addStoryBinding: ActivityAddStoryBinding? = null,
    private val googleMap: GoogleMap? = null,
    private val user: User? = null
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context!!, activity!!)) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                Injection.provideLoginRepository(
                    activity!!,
                    email!!,
                    password!!,
                    user!!,
                    loginBinding!!
                )
            ) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(
                Injection.provideRegisterRepository(
                    activity = activity!!,
                    name = name!!,
                    email = email!!,
                    password = password!!,
                    binding = registerBinding!!
                )
            ) as T
        } else if (modelClass.isAssignableFrom(DetailStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailStoryViewModel(
                Injection.provideDetailStoryRepository(
                    activity = activity!!,
                    binding = detailStoryBinding!!
                )
            ) as T
        } else if (modelClass.isAssignableFrom(StoriesLocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoriesLocationViewModel(
                Injection.provideStoryLocationRepository(
                    context = context!!,
                    googleMap = googleMap!!,
                    activity = activity!!
                )
            ) as T
        } else if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddStoryViewModel(
                Injection.provideAddStoryRepository(
                    context = context!!,
                    binding = addStoryBinding!!,
                    activity = activity!!
                )
            ) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(
                Injection.provideAuthRepository(
                    context = context!!,
                    activity = activity!!
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}