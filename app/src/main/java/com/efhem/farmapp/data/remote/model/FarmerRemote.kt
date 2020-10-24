package com.efhem.farmapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class FarmerRemote(
    @SerializedName("address")
    val address: String,
    @SerializedName("bvn")
    val bvn: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email_address")
    val emailAddress: String,
    @SerializedName("expiry_date")
    val expiryDate: String,
    @SerializedName("farmer_id")
    val farmerId: String,
    @SerializedName("fingerprint")
    val fingerprint: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id_image")
    val idImage: String,
    @SerializedName("id_no")
    val idNo: String,
    @SerializedName("id_type")
    val idType: String,
    @SerializedName("issue_date")
    val issueDate: String,
    @SerializedName("lga")
    val lga: String,
    @SerializedName("marital_status")
    val maritalStatus: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mobile_no")
    val mobileNo: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("occupation")
    val occupation: String,
    @SerializedName("passport_photo")
    val passportPhoto: String,
    @SerializedName("reg_no")
    val regNo: String,
    @SerializedName("spouse_name")
    val spouseName: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("surname")
    val surname: String
)