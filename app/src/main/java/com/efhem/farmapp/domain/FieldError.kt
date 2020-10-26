package com.efhem.farmapp.domain

class FieldError(val field: Field, val error: String = "Invalid", val isError: Boolean = true)

enum class Field {FIRST_NAME, SURNAME, CITY, EMAIL, DOB, GENDER, AVATAR }