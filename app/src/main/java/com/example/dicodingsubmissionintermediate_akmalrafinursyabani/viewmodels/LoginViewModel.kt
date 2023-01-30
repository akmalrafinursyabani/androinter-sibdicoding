package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.LoginRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
) : ViewModel() {
    fun login(email: String, password: String) = loginRepository.login(email, password)

}