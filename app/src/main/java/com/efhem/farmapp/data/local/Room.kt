package com.efhem.farmapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [], version = 1, exportSchema = false)
abstract class FarmAppDatabase : RoomDatabase() {

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