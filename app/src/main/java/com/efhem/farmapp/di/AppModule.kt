package com.efhem.farmapp.di

import com.efhem.farmapp.data.local.StoragePref
import com.efhem.farmapp.data.local.database
import com.efhem.farmapp.data.local.mappers.FarmLocalModelMapper
import com.efhem.farmapp.data.local.mappers.FarmerLocalModelMapper
import com.efhem.farmapp.data.remote.RemoteApi
import com.efhem.farmapp.data.remote.createNetworkClient
import com.efhem.farmapp.data.remote.datasource.FarmRepo
import com.efhem.farmapp.data.remote.datasource.FarmerLocalRepo
import com.efhem.farmapp.data.remote.datasource.FarmerRemoteRepo
import com.efhem.farmapp.data.remote.mappers.FarmersRemoteModelMapper
import com.efhem.farmapp.domain.repositories.FarmerRepository
import com.efhem.farmapp.domain.repositories.IFarmLocalRepo
import com.efhem.farmapp.domain.repositories.IFarmerLocalRepo
import com.efhem.farmapp.domain.repositories.IFarmerRemoteRepo
import com.efhem.farmapp.ui.FarmViewModel
import com.efhem.farmapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val mNetworkModules = module {
    single { createNetworkClient("https://theagromall.com/api/v2/").create(RemoteApi::class.java) }
}

val mLocalModules = module {
    single { database(androidContext()) }
    single { StoragePref(get()) }
}

val mRepositoryModules = module {

    //farmer
    single { FarmerLocalRepo( get(), farmerLocalModelMapper = FarmerLocalModelMapper()) as IFarmerLocalRepo }
    single { FarmerRemoteRepo(api = get(), farmersRemoteModelMapper = FarmersRemoteModelMapper()) as IFarmerRemoteRepo }
    single { FarmerRepository(local = get(), remote = get()) }

    //farm
    single { FarmRepo(database = get(), farmLocalModelMapper = FarmLocalModelMapper()) }

}

val mViewModelsModules = module {

    viewModel { MainViewModel(get()) }
    viewModel { FarmViewModel(get(), get()) }

}

private const val DATABASE = "database"
private const val SHARE_PREFERENCE = "sharePreference"