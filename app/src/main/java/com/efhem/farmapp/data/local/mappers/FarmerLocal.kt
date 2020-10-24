package com.efhem.farmapp.data.local.mappers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FarmerLocal(
    @PrimaryKey
    val id: String,
    val surname: String,
    val firstName: String,
    val city: String,
    val email: String,
    val gender: String,
    val dob: String,
    val avatar: String
)