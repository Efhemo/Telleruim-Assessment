package com.efhem.farmapp.data.remote.mappers

import com.efhem.farmapp.data.remote.model.FarmerRemote
import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.mappers.BaseMapper

class FarmersRemoteModelMapper : BaseMapper<FarmerRemote, Farmer> {

    override fun mapToDomain(type: FarmerRemote): Farmer = Farmer(
        type.farmerId,
        type.surname,
        type.firstName,
        type.city,
        type.emailAddress,
        type.gender,
        type.dob,
        type.passportPhoto
    )

    override fun mapToDto(type: Farmer): FarmerRemote = FarmerRemote(
        type.city,
        type.dob,
        type.email,
        type.id,
        type.firstName,
        type.gender,
        type.avatar,
        type.surname
    )

}