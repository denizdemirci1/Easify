package com.deniz.easify.util

import androidx.multidex.MultiDexApplication
import com.deniz.easify.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class Easify : MultiDexApplication() {

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
