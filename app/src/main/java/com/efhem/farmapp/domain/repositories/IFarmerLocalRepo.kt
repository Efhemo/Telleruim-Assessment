package com.efhem.farmapp.domain.repositories

import androidx.lifecycle.LiveData
import com.efhem.farmapp.domain.Farmer

interface IFarmerLocalRepo {

    suspend fun getFarmers(): List<Farmer>
    suspend fun saveFarmer(farmer: Farmer)
    suspend fun saveFarmers(farmers: List<Farmer>)
    suspend fun deleteAllFarmers()
    fun observableFarmers(): LiveData<List<Farmer>>
}