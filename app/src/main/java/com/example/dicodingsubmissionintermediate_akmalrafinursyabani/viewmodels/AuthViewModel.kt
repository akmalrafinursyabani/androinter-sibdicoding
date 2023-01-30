package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun logout() = authRepository.logout()
}