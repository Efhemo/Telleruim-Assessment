package com.efhem.farmapp.domain.repositories

import androidx.lifecycle.LiveData
import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.ResultWrapper


class FarmerRepository(private val local: IFarmerLocalRepo,
                       private val remote: IFarmerRemoteRepo
) : IFarmerRepository {

    override fun observableFarmers(): LiveData<List<Farmer>> = local.observableFarmers()

    override suspend fun fetchFarmers(): ResultWrapper<List<Farmer>> {
        val result = remote.getFarmers()
        if (result is ResultWrapper.Success){
            local.saveFarmers(result.data) }
        return result
    }

    override suspend fun atLeastHasOneFarmer(): Boolean = local.getFarmers().isNotEmpty()
    override suspend fun saveFarmer(farmer: Farmer) {
        local.saveFarmer(farmer)
    }

    override suspend fun getFarmer(farmerId: String): Farmer {
        return local.getFarmer(farmerId)
    }

}