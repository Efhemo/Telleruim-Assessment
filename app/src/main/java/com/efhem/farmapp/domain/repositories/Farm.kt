package com.efhem.farmapp.domain.repositories

class Farm (
    val farmerId: String,
    val name: String,
    val location : List<Coordinate>
)