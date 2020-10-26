package com.efhem.farmapp.data.local.mappers

import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.mappers.BaseMapper
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.Utils

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
            K.BASE_IMAGE_URL + Utils.removeBackSlash(type.avatar)
        )
    }

}