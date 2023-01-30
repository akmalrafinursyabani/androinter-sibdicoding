package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.RegisterResponse

object DataRegisterDummy {
    fun initDataRegisterDummy(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "Berhasil register"
        )
    }
}