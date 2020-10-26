package com.efhem.farmapp.domain.repositories

import androidx.lifecycle.LiveData
import com.efhem.farmapp.domain.model.Farm

interface IFarmLocalRepo {

    suspend fun getFarms(id: String): List<Farm>
    suspend fun saveFarm(farm: Farm)
    suspend fun saveFarms(farms: List<Farm>)
    suspend fun deleteAllFarm()
    fun observableFarms(): LiveData<List<Farm>>
}