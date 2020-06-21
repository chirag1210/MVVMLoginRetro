package com.mycode.mvvmkotlin.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val token: String
)