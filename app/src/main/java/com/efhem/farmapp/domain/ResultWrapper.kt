package com.efhem.farmapp.domain

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultWrapper<out R> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val errorData: ErrorData) : ResultWrapper<Nothing>()
    data class NetworkError(val message: String = "Check your network connection"): ResultWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$errorData]"
            is NetworkError -> "Error[exception=$message]"
        }
    }
}

/**
 * `true` if [ResultWrapper] is of type [Success] & holds non-null [Success.data].
 */
val ResultWrapper<*>.succeeded
    get() = this is ResultWrapper.Success && data != null
