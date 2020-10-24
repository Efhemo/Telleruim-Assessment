package com.efhem.farmapp.di

import com.efhem.farmapp.data.local.StoragePref
import com.efhem.farmapp.data.local.database
import com.efhem.farmapp.data.remote.RemoteApi
import com.efhem.farmapp.data.remote.createNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val mNetworkModules = module {
    single { createNetworkClient("https://theagromall.com/api/v2/").create(RemoteApi::class.java) }
}

val mLocalModules = module {
    single { database(androidContext()) }
    single { StoragePref(get()) }
}

val mRepositoryModules = module {

}

val mViewModelsModules = module {


}

private const val DATABASE = "database"
private const val SHARE_PREFERENCE = "sharePreference"