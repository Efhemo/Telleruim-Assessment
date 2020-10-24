package com.efhem.farmapp.data.remote

import com.efhem.farmapp.data.remote.model.FarmerRemote
import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
    @SerializedName("data")
    var payload: T? = null
        private set
    @SerializedName("status")
    var responseCode: Boolean? = null

    fun setResponse(response: T) {
        this.payload = response
    }
}

data class FarmersListResponse(
        @SerializedName("farmers")
        val farmerRemotes: List<FarmerRemote>,
        @SerializedName("imageBaseUrl")
        val imageBaseUrl: String,
        @SerializedName("totalRec")
        val totalRec: Int
)

class FarmersResponse : BaseResponse<FarmersListResponse>()


