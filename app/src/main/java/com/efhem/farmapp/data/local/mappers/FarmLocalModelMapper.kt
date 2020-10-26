package com.efhem.farmapp.data.local.mappers

import com.efhem.farmapp.domain.mappers.BaseMapper
import com.efhem.farmapp.domain.model.Farm

class FarmLocalModelMapper : BaseMapper<FarmLocal, Farm> {

    override fun mapToDomain(type: FarmLocal): Farm {
        return Farm(
            type.farmerId,
            type.name,
            type.locations
        )
    }

    override fun mapToDto(type: Farm): FarmLocal {
        return FarmLocal(
            farmerId = type.farmerId,
            name = type.name,
            locations = type.locations
        )
    }

}