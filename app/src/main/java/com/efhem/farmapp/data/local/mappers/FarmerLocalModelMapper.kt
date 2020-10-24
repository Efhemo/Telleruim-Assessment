package com.efhem.farmapp.data.local.mappers

import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.mappers.BaseMapper

class FarmerLocalModelMapper : BaseMapper<FarmerLocal, Farmer> {

    override fun mapToDomain(type: FarmerLocal): Farmer {
        return Farmer(
            type.id,
            type.surname,
            type.firstName,
            type.city,
            type.email,
            type.gender,
            type.dob,
            type.avatar
        )
    }

    override fun mapToDto(type: Farmer): FarmerLocal {
        return FarmerLocal(
            type.id,
            type.surname,
            type.firstName,
            type.city,
            type.email,
            type.gender,
            type.dob,
            type.avatar
        )
    }

}