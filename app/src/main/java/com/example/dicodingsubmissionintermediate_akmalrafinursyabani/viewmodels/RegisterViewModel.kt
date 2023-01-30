package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        registerRepository.register(name, email, password)

}