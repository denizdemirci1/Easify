package com.deniz.easify.di

import com.deniz.easify.ui.history.HistoryViewModel
import com.deniz.easify.ui.profile.ProfileViewModel
import com.deniz.easify.ui.profile.favorites.FavoritesViewModel
import com.deniz.easify.ui.profile.favorites.topArtists.TopArtistsViewModel
import com.deniz.easify.ui.profile.favorites.topTracks.TopTracksViewModel
import com.deniz.easify.ui.profile.followings.FollowedArtistsViewModel
import com.deniz.easify.ui.profile.followings.artist.ArtistViewModel
import com.deniz.easify.ui.profile.followings.follow.FollowViewModel
import com.deniz.easify.ui.profile.playlists.PlaylistViewModel
import com.deniz.easify.ui.profile.playlists.create.CreatePlaylistViewModel
import com.deniz.easify.ui.profile.playlists.detail.PlaylistDetailViewModel
import com.deniz.easify.ui.search.SearchViewModel
import com.deniz.easify.ui.search.features.FeaturesViewModel
import com.deniz.easify.ui.search.features.discover.DiscoverViewModel
import com.deniz.easify.ui.search.features.discover.recommended.RecommendedTracksViewModel
import com.deniz.easify.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { TopArtistsViewModel() }
    viewModel { TopTracksViewModel() }
    viewModel { FollowedArtistsViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { ArtistViewModel(get()) }
    viewModel { FeaturesViewModel(get()) }
    viewModel { DiscoverViewModel() }
    viewModel { RecommendedTracksViewModel(get()) }
    viewModel { PlaylistViewModel(get(), get()) }
    viewModel { CreatePlaylistViewModel(get()) }
    viewModel { PlaylistDetailViewModel(get()) }
}
