package com.efhem.farmapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.Field
import com.efhem.farmapp.domain.FieldError

class FarmViewModel : ViewModel() {

    private val _observableFarmer = MutableLiveData<Farmer>()
    val observableFarmer: MutableLiveData<Farmer> = _observableFarmer

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<FieldError>()
    val error: LiveData<FieldError> = _error

    var gender: String? = null

    fun setFarmer(farmer: Farmer) {
        _observableFarmer.value = farmer
    }

    val fields: HashMap<String, String?> = mapOf<String, String?>(
        "surname" to null,
        "firstname" to null,
        "city" to null,
        "email" to null,
        "dob" to null,
        "farmname" to null
    ) as HashMap<String, String?>

    fun isFormValidated(): Boolean {
        var isStaticFormValid = true

        for ((key, value) in fields) {

            isStaticFormValid = when (key) {
                "surname" -> validate(Field.SURNAME, value)
                "firstname" -> validate(Field.FIRST_NAME, value)
                "city" -> validate(Field.CITY, value)
                "email" -> validate(Field.EMAIL, value)
                "dob" -> validate(Field.DOB, value)
                "farmname" -> validate(Field.FARM_NAME, value)
                else -> {
                    if (gender == null) {
                        FieldError(Field.GENDER)
                        false
                    } else true
                }
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


}