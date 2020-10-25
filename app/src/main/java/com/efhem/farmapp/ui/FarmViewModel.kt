package com.efhem.farmapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.domain.Field
import com.efhem.farmapp.domain.FieldError

class FarmViewModel : ViewModel() {

    private val _observableFarmer = MutableLiveData<Farmer?>()
    val observableFarmer: LiveData<Farmer?> = _observableFarmer

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<FieldError?>()
    val error: LiveData<FieldError?> = _error

    var farmerId: String? = null

    fun setFarmer(farmer: Farmer) {
        _observableFarmer.value = farmer
        farmerId = farmer.id
        setAvatar(farmer.avatar)
    }

    fun setAvatar(filePath: String){
        fields["avatar"] = filePath
    }
    fun setGender(gender: String){
        fields["gender"] = gender
    }

    var fields: HashMap<String, String?> = hashMap()

    private fun hashMap(): HashMap<String, String?> {
        return mapOf<String, String?>(
            "surname" to null,
            "firstname" to null,
            "city" to null,
            "email" to null,
            "dob" to null,
            "farmname" to null,
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
                "farmname" -> validate(Field.FARM_NAME, value)
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

    fun getFilledFarmerForm(): Farmer {
        return if(farmerId == null){
            Farmer(
                surname = fields["surname"]!!, firstName = fields["firstname"]!!,
                city = fields["city"]!!, email =  fields["email"]!!, gender = fields["gender"]!!,
                dob = fields["dob"]!!, avatar = fields["avatar"]!!
            )
        }else Farmer(
            farmerId!!, fields["surname"]!!, fields["firstname"]!!,
            fields["city"]!!, fields["email"]!!, gender = fields["gender"]!!,
            fields["dob"]!!, fields["avatar"]!!
        )
    }

    fun clear() {
        farmerId = null
        _observableFarmer.value = null
        _error.value = null
        fields = hashMap()
    }

}