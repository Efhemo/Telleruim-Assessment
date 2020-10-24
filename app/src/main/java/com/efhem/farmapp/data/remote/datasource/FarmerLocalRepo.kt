package com.efhem.farmapp.data.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.efhem.farmapp.data.local.FarmAppDatabase
import com.efhem.farmapp.data.local.dao.FarmerDao
import com.efhem.farmapp.data.local.mappers.FarmerLocalModelMapper
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.repositories.IFarmerLocalRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FarmerLocalRepo(database: FarmAppDatabase, private val farmerLocalModelMapper: FarmerLocalModelMapper,
                      private val ioDispatcher: CoroutineDispatcher =
                            Dispatchers.IO) : IFarmerLocalRepo {

    private val farmerDao: FarmerDao = database.daoFarmer()
    override suspend fun getFarmers(): List<Farmer> = withContext(ioDispatcher) {
        farmerLocalModelMapper.mapToDomainList(farmerDao.getFarmers()) }

    override suspend fun saveFarmers(farmers: List<Farmer>) {
        farmerDao.insertFarmers(farmerLocalModelMapper.mapToDtoList(farmers))
    }

    override suspend fun deleteAllFarmers() = farmerDao.deleteAllFarmer()


    override fun observableFarmers(): LiveData<List<Farmer>>  =
        Transformations.map(farmerDao.observeFarmers()){
            farmerLocalModelMapper.mapToDomainList(it)
        }


    override suspend fun saveFarmer(farmer: Farmer) {
        farmerDao.insertFarmer(farmerLocalModelMapper.mapToDto(farmer))
    }

}