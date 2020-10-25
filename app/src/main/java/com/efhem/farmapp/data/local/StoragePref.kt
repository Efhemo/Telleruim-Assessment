package com.efhem.farmapp.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility class for management of Shared Preferences storage.
 */
class StoragePref(context: Context) {

    var preferences: SharedPreferences =
        context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)


    companion object {
        private const val STORAGE = "com.efhem.farmapp.data.local.STORAGE"

    }
}