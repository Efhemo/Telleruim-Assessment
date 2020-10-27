package com.efhem.farmapp.ui.main

import androidx.lifecycle.*
import com.efhem.farmapp.data.remote.datasource.FarmRepo
import com.efhem.farmapp.domain.model.Farmer

import com.efhem.farmapp.domain.ResultWrapper
import com.efhem.farmapp.domain.model.Statistic
import com.efhem.farmapp.domain.repositories.FarmerRepository
import kotlinx.coroutines.launch

class MainViewModel(private val farmRepo: FarmRepo,  private val farmerRepository: FarmerRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    val observableFarmers: LiveData<List<Farmer>> = farmerRepository.observableFarmers()

    val farmStat = observableFarmers.combineWithTwo(
        farmRepo.observableFarms(), MutableLiveData<String>()){ farmers, farms, empty ->

        val farmersCount = farmers?.size
        val farmersWithEmail = farmers?.filter { it.email.isNotEmpty() }?.size
        val farmerWithFarm = farms?.distinctBy { it.farmerId }?.size

        if((farmersCount != null) and (farmersWithEmail != null) and (farmerWithFarm != null)){
            var farmersWithEmailPercentage = 0.0
            if(farmersWithEmail!! < farmersCount!!){
                val result  = (farmersWithEmail.toDouble() / farmersCount) * 100
                farmersWithEmailPercentage = String.format("%.2f", result).toDouble()
            }
            var farmersWithFarm = 0.0
            if(farmerWithFarm!! < farmersCount){
                val result = (farmerWithFarm.toDouble() / farmersCount) * 100
                farmersWithFarm = String.format("%.2f", result).toDouble()
            }
            Statistic(farmersCount, farmersWithEmailPercentage, farmersWithFarm)
        }else Statistic(0, 0.0, 0.0)
    }

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    init { fetchFarmers() }

    fun fetchFarmers() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = farmerRepository.fetchFarmers()) {
                is ResultWrapper.Error -> _error.value = result.errorData.message
                is ResultWrapper.NetworkError -> _error.value = result.message
            }
            _loading.value = false
        }
    }

    private fun <T, K, F, R> LiveData<T>.combineWithTwo(
        liveData: LiveData<K>,
        liveData2: LiveData<F>,
        callback: (T?, K?, F?) -> R
    ): MutableLiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = callback(this.value, liveData.value, liveData2.value)
        }
        result.addSource(liveData) {
            result.value = callback(this.value, liveData.value, liveData2.value)
        }
        result.addSource(liveData2) {
            result.value = callback(this.value, liveData.value, liveData2.value)
        }
        return result
    }

}