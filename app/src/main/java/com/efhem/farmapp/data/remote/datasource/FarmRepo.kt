package com.efhem.farmapp.data.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.efhem.farmapp.data.local.FarmAppDatabase
import com.efhem.farmapp.data.local.dao.FarmDao
import com.efhem.farmapp.data.local.mappers.FarmLocalModelMapper
import com.efhem.farmapp.domain.model.Farm
import com.efhem.farmapp.domain.repositories.IFarmLocalRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FarmRepo(database: FarmAppDatabase, private val farmLocalModelMapper: FarmLocalModelMapper,
               private val ioDispatcher: CoroutineDispatcher =
                            Dispatchers.IO) : IFarmLocalRepo {

    private val farmDao: FarmDao = database.daoFarm()
    override suspend fun getFarms(id: String): List<Farm> = withContext(ioDispatcher) {
        farmLocalModelMapper.mapToDomainList(farmDao.getFarms(id)) }

    override suspend fun saveFarms(farms: List<Farm>) {
        farmDao.insertFarms(farmLocalModelMapper.mapToDtoList(farms))
    }

    override suspend fun deleteAllFarm() = farmDao.deleteAllFarms()


    override fun observableFarms(): LiveData<List<Farm>>  =
        Transformations.map(farmDao.observeFarms()){
            farmLocalModelMapper.mapToDomainList(it)
        }


    override suspend fun saveFarm(farm: Farm) {
        farmDao.insertFarm(farmLocalModelMapper.mapToDto(farm))
    }

}