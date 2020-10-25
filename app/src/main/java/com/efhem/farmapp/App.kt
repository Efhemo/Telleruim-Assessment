package com.efhem.farmapp

import android.app.Application
import com.efhem.farmapp.di.mLocalModules
import com.efhem.farmapp.di.mNetworkModules
import com.efhem.farmapp.di.mRepositoryModules
import com.efhem.farmapp.di.mViewModelsModules
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        loadKoin()
    }

    private fun loadKoin() {
        startKoin(this, listOf(mLocalModules, mNetworkModules, mViewModelsModules, mRepositoryModules))
    }
}