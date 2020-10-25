package com.efhem.farmapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Farmer(
    val id: String,
    val surname: String,
    val firstName: String,
    val city: String,
    val email: String,
    val gender: String,
    val dob: String,
    val avatar: String
) : Parcelable