package com.example.dicodingsubmissionintermediate_akmalrafinursyabani

import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResult

object DataLoginDummy {
    fun initDataLoginDummy(): LoginResponse {

        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResult(
                userId = "user-GHopxAhKUgYEB39h",
                name = "Akmal Rafi",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUdIb3B4QWhLVWdZRUIzOWgiLCJpYXQiOjE2NzE1MDUwNDJ9.3jhkrnOFKuW3zs7G280eGf2nJ-nmtCWrws4HjbSynSk"
            )
        )
    }
}