package com.deniz.easify.util

import android.app.Application
import com.deniz.easify.di.managerModule
import com.deniz.easify.di.repositoryModule
import com.deniz.easify.di.retrofitModule
import com.deniz.easify.di.serviceModule
import com.deniz.easify.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class Easify : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Easify)
            modules(listOf(repositoryModule,
                viewModelModule,
                retrofitModule,
                serviceModule,
                managerModule))
        }
    }
}
