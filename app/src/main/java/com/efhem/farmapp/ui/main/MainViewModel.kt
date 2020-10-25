package com.efhem.farmapp.ui.main

import com.efhem.farmapp.domain.Farmer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efhem.farmapp.domain.ResultWrapper
import com.efhem.farmapp.domain.repositories.FarmerRepository
import kotlinx.coroutines.launch

class MainViewModel(private val farmerRepository: FarmerRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _observableFarmers = MutableLiveData<List<Farmer>>()
    val observableFarmers: MutableLiveData<List<Farmer>> = _observableFarmers

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    init { fetchFarmers() }

    fun fetchFarmers() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = farmerRepository.fetchFarmers()) {
                is ResultWrapper.Success -> result.data.let { _observableFarmers.value = it }
                is ResultWrapper.Error -> _error.value = result.errorData.message
                is ResultWrapper.NetworkError -> _error.value = result.message
            }
            _loading.value = false
        }
    }

}