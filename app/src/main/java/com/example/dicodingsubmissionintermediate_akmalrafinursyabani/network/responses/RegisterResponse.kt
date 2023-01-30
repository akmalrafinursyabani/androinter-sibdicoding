package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
