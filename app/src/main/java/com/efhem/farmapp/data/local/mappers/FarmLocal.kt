package com.efhem.farmapp.data.local.mappers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efhem.farmapp.domain.model.Coordinate
import java.util.*

@Entity
class FarmLocal (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val farmerId: String,
    val name: String,
    val locations : List<Coordinate>
)