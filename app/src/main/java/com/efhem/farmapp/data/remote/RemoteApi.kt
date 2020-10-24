package com.efhem.farmapp.data.remote

import retrofit2.Response
import retrofit2.http.*

interface RemoteApi {

    @GET("get-sample-farmers")
    suspend fun farmers(
            @Query("limit") limit: Int
    ): Response<FarmersResponse>
}