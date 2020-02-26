package com.deniz.easify.di

import com.deniz.easify.util.AuthManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

val utilsModule = module {
    single { AuthManager(androidApplication()) }
}
