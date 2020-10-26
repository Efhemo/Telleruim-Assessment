package com.efhem.farmapp.domain.repositories

import androidx.lifecycle.LiveData
import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.ResultWrapper

interface IFarmerRepository {

    fun observableFarmers(): LiveData<List<Farmer>>

    suspend fun fetchFarmers(): ResultWrapper<List<Farmer>>

    suspend fun atLeastHasOneFarmer(): Boolean

    suspend fun saveFarmer(farmer: Farmer)
    suspend fun getFarmer(farmerId: String): Farmer
}