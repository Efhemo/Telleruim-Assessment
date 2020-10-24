package com.efhem.farmapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class FarmerRemote(
    @SerializedName("city")
    val city: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email_address")
    val emailAddress: String,
    @SerializedName("farmer_id")
    val farmerId: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("passport_photo")
    val passportPhoto: String,
    @SerializedName("surname")
    val surname: String
)