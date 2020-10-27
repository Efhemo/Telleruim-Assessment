package com.efhem.farmapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Utility class for management of Shared Preferences storage.
 */
class StoragePref(context: Context) {

    var preferences: SharedPreferences =
        context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)


    var userEmail: String
        get() = preferences.getString(USER_EMAIL, "test@tellerium.io") ?: "test@tellerium.io"
        set(value) = preferences.edit { putString(USER_EMAIL, value) }


    var accessUserPassword: String
        get() = preferences.getString(ACCESS_PASSWORD, "password") ?: "password"
        set(value) = preferences.edit { putString(ACCESS_PASSWORD, value) }


    companion object {
        private const val STORAGE = "com.efhem.farmapp.data.local.STORAGE"

        private const val USER_EMAIL = "user_email"

        private const val ACCESS_PASSWORD = "access_password"
    }
}