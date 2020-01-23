package com.deniz.easify.di

import android.content.Context
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.SpotifyInterceptor
import com.deniz.easify.data.source.remote.SpotifyService
import com.deniz.easify.ui.profile.ProfileViewModel
import com.deniz.easify.ui.profile.favorites.FavoritesViewModel
import com.deniz.easify.ui.profile.favorites.topArtists.TopArtistsViewModel
import com.deniz.easify.ui.profile.favorites.topTracks.TopTracksViewModel
import com.deniz.easify.ui.profile.followings.FollowedArtistsViewModel
import com.deniz.easify.ui.profile.followings.artist.ArtistViewModel
import com.deniz.easify.ui.profile.followings.follow.FollowViewModel
import com.deniz.easify.ui.profile.playlists.PlaylistViewModel
import com.deniz.easify.ui.profile.playlists.detail.PlaylistDetailViewModel
import com.deniz.easify.ui.search.SearchViewModel
import com.deniz.easify.ui.search.features.FeaturesViewModel
import com.deniz.easify.ui.search.features.discover.DiscoverViewModel
import com.deniz.easify.ui.search.features.discover.recommended.RecommendedTracksViewModel
import com.deniz.easify.ui.splash.SplashViewModel
import com.deniz.easify.ui.track.TrackViewModel
import com.deniz.easify.util.AuthManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @User: deniz.demirci
 * @Date: 2019-11-20
 */

val viewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { TrackViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { TopArtistsViewModel(get()) }
    viewModel { TopTracksViewModel(get()) }
    viewModel { FollowedArtistsViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { ArtistViewModel(get()) }
    viewModel { FeaturesViewModel(get()) }
    viewModel { DiscoverViewModel(get()) }
    viewModel { RecommendedTracksViewModel() }
    viewModel { PlaylistViewModel(get(), get()) }
    viewModel { PlaylistDetailViewModel(get()) }
}

val repositoryModule = module {
    single<Repository> { SpotifyRepository(get()) }
}

val managerModule = module {
    fun provideAuthManager(context: Context): AuthManager {
        return AuthManager(context)
    }
    single { provideAuthManager(androidApplication()) }
}

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
