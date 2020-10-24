package com.efhem.farmapp.domain

import com.google.gson.annotations.SerializedName

class ErrorData (
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("error_description")
    val message: String)