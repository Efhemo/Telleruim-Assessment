package com.efhem.farmapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efhem.farmapp.data.remote.datasource.FarmRepo
import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.Field
import com.efhem.farmapp.domain.FieldError
import com.efhem.farmapp.domain.model.Coordinate
import com.efhem.farmapp.domain.model.Farm
import com.efhem.farmapp.domain.repositories.FarmerRepository
import kotlinx.coroutines.launch

class FarmViewModel(private val farmRepo: FarmRepo, private val farmerRepo: FarmerRepository) : ViewModel() {

    private val _observableFarmer = MutableLiveData<Farmer?>()
    val observableFarmer: LiveData<Farmer?> = _observableFarmer

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<FieldError?>()
    val error: LiveData<FieldError?> = _error

    var farmerId: String? = null
    var location: String? = null

    //farm
    private val _observeFarms = MutableLiveData<List<Farm>?>()
    val observeFarms: LiveData<List<Farm>?> = _observeFarms

    //new farms
    private var newFarm = ArrayList<Farm>()
    fun addFarm(farm: Farm) {
        newFarm.add(farm)
    }

    fun setFarmer(farmer: Farmer) {
        _observableFarmer.value = farmer
        farmerId = farmer.id
        getFarms(farmerId)
        setAvatar(farmer.avatar)
    }

    private fun getFarms(farmerId: String?) {
        _loading.value = true
        viewModelScope.launch {
            farmerId?.let {
                val result = farmRepo.getFarms(it)
                if (!result.isNullOrEmpty()) {
                    _observeFarms.value = result
                }
            }
            _loading.value = false
        }
    }

    fun setAvatar(filePath: String) {
        fields["avatar"] = filePath
    }

    fun setGender(gender: String) {
        fields["gender"] = gender
    }

    fun saveFarms(isFinished: (Boolean) -> Unit) {
        _loading.value = true
        viewModelScope.launch {
            val farmer = getFilledFarmerForm()
            farmerRepo.saveFarmer(farmer)
            val savedFarmer = farmerRepo.getFarmer(farmer.id)
            val farms = newFarm.map { Farm(savedFarmer.id, it.name, it.locations) }
            farmRepo.saveFarms(farms)
            _loading.value = false
            isFinished(true)
        }
    }

    var fields: HashMap<String, String?> = hashMap()

    private fun hashMap(): HashMap<String, String?> {
        return mapOf<String, String?>(
            "surname" to null,
            "firstname" to null,
            "city" to null,
            "email" to null,
            "dob" to null,
            "gender" to null,
            "avatar" to null
        ) as HashMap<String, String?>
    }

    fun isFormValidated(): Boolean {
        var isStaticFormValid = true

        for ((key, value) in fields) {

            isStaticFormValid = when (key) {
                "surname" -> validate(Field.SURNAME, value)
                "firstname" -> validate(Field.FIRST_NAME, value)
                "city" -> validate(Field.CITY, value)
                "email" -> validate(Field.EMAIL, value)
                "dob" -> validate(Field.DOB, value)
                "avatar" -> validate(Field.AVATAR, value)
                else -> validate(Field.GENDER, value)
            }
            if (isStaticFormValid.not()) break
        }
        return isStaticFormValid
    }

    private fun validate(fieldType: Field, value: String?): Boolean {
        return if (value.isNullOrEmpty()) {
            _error.value = FieldError(fieldType)
            false
        } else {
            _error.value = FieldError(fieldType, isError = false)
            true
        }
    }

    private fun getFilledFarmerForm(): Farmer {
        return if (farmerId == null) {
            Farmer(
                surname = fields["surname"]!!, firstName = fields["firstname"]!!,
                city = fields["city"]!!, email = fields["email"]!!, gender = fields["gender"]!!,
                dob = fields["dob"]!!, avatar = fields["avatar"]!!
            )
        } else Farmer(
            farmerId!!, fields["surname"]!!, fields["firstname"]!!,
            fields["city"]!!, fields["email"]!!, gender = fields["gender"]!!,
            fields["dob"]!!, fields["avatar"]!!
        )
    }

    fun clear() {
        farmerId = null
        _observableFarmer.value = null
        _observeFarms.value = null
        location = null
        _error.value = null
        newFarm = ArrayList()
        fields = hashMap()
    }

}