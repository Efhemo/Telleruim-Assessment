package com.efhem.farmapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Farm (
    val farmerId: String,
    val name: String,
    val locations : List<Coordinate>
): Parcelable