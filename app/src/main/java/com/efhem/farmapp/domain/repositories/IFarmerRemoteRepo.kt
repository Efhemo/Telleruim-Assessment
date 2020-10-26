package com.efhem.farmapp.domain.repositories

import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.ResultWrapper

interface IFarmerRemoteRepo {
    suspend fun getFarmers(): ResultWrapper<List<Farmer>>
}