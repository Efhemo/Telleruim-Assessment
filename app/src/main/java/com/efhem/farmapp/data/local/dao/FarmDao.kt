package com.efhem.farmapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.efhem.farmapp.data.local.mappers.FarmLocal

@Dao
interface FarmDao {

    @Query("SELECT * FROM farmlocal WHERE farmerId = :farmId")
    suspend fun getFarms(farmId: String): List<FarmLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarms(form: List<FarmLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarm(form: FarmLocal)

    @Query("SELECT * FROM farmlocal")
    fun observeFarms(): LiveData<List<FarmLocal>>

    @Query("DELETE FROM farmlocal")
    suspend fun deleteAllFarms()
}
