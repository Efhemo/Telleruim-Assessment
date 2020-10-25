package com.efhem.farmapp.domain.repositories

import androidx.lifecycle.LiveData
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.ResultWrapper

interface IFarmerRepository {

    fun observableFarmers(): LiveData<List<Farmer>>

    suspend fun fetchFarmers(): ResultWrapper<List<Farmer>>

    suspend fun atLeastHasOneFarmer(): Boolean
}