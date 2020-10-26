package com.efhem.farmapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Farmer(
    val id: String = UUID.randomUUID().toString(),
    val surname: String,
    val firstName: String,
    val city: String,
    val email: String,
    val gender: String,
    val dob: String,
    val avatar: String
) : Parcelable {

    override fun toString(): String {
        return "Farmer(id='$id', surname='$surname', firstName='$firstName', city='$city'," +
                " email='$email', gender='$gender', dob='$dob', avatar='$avatar')"
    }
}