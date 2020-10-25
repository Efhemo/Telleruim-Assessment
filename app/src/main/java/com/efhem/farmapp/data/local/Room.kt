package com.efhem.farmapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.efhem.farmapp.data.local.dao.FarmerDao
import com.efhem.farmapp.data.local.mappers.FarmerLocal


@Database(entities = [FarmerLocal::class], version = 1, exportSchema = false)
abstract class FarmAppDatabase : RoomDatabase() {
    abstract fun daoFarmer(): FarmerDao
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