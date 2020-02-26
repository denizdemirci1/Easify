package com.deniz.easify.di

import android.content.Context
import com.deniz.easify.data.source.remote.service.SpotifyService
import com.deniz.easify.data.source.remote.utils.SpotifyInterceptor
import com.deniz.easify.util.AuthManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

val serviceModule = module {
    fun provideService(retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }

    single { provideService(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(context: Context): OkHttpClient {
        val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(
                SpotifyInterceptor(
                    AuthManager(context)
                )
            )

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SpotifyService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient(androidApplication()) }
    single { provideRetrofit(get(), get()) }
}
