package com.efhem.farmapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.efhem.farmapp.data.local.mappers.FarmerLocal

@Dao
interface FarmerDao {

    @Query("SELECT * FROM farmerlocal")
    suspend fun getFarmers(): List<FarmerLocal>

    @Query("SELECT * FROM farmerlocal WHERE id = :farmerId ")
    suspend fun getFarmer(farmerId: String): FarmerLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmers(form: List<FarmerLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmer(form: FarmerLocal)

    @Query("SELECT * FROM farmerlocal ORDER BY id DESC")
    fun observeFarmers(): LiveData<List<FarmerLocal>>

    @Query("DELETE FROM farmerlocal")
    suspend fun deleteAllFarmer()
}
