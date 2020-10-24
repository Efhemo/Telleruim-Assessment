package com.efhem.farmapp.data.remote.datasource

import com.efhem.farmapp.data.remote.RemoteApi
import com.efhem.farmapp.data.remote.mappers.FarmersRemoteModelMapper
import com.efhem.farmapp.data.remote.safeApiResult
import com.efhem.farmapp.domain.ErrorData
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.ResultWrapper
import com.efhem.farmapp.domain.repositories.IFarmerRemoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class FarmerRemoteRepo(
    private val api: RemoteApi,
    private val farmersRemoteModelMapper: FarmersRemoteModelMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IFarmerRemoteRepo {

    override suspend fun getFarmers(): ResultWrapper<List<Farmer>> {
        return when (val result =
            safeApiResult(ioDispatcher) { api.farmers(200) }) {
            is ResultWrapper.Success -> {
                val payload = result.data.payload
                if (payload?.farmerRemotes == null) {
                    ResultWrapper.Error(ErrorData(message = "Empty"))
                } else ResultWrapper.Success(farmersRemoteModelMapper.mapToDomainList(payload.farmerRemotes))
            }
            is ResultWrapper.Error -> ResultWrapper.Error(result.errorData)
            else -> ResultWrapper.NetworkError()
        }
    }
}