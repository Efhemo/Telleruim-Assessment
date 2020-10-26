package com.efhem.farmapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.efhem.farmapp.data.local.dao.FarmDao
import com.efhem.farmapp.data.local.dao.FarmerDao
import com.efhem.farmapp.data.local.mappers.FarmLocal
import com.efhem.farmapp.data.local.mappers.FarmerLocal
import com.efhem.farmapp.util.CoordinateTypeConverter


@Database(entities = [FarmerLocal::class, FarmLocal::class], version = 1, exportSchema = false)
@TypeConverters(CoordinateTypeConverter::class )
abstract class FarmAppDatabase : RoomDatabase() {
    abstract fun daoFarmer(): FarmerDao
    abstract fun daoFarm(): FarmDao
}

private lateinit var INSTANCE: FarmAppDatabase

fun database(context: Context): FarmAppDatabase {
    synchronized(FarmAppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                FarmAppDatabase::class.java, "FarmAppDatabase"
            ).build()
        }
    }
    return INSTANCE
}