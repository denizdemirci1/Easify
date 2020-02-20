package com.deniz.easify.di

import com.deniz.easify.data.source.repositories.*
import org.koin.dsl.module

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

val repositoryModule = module {
    single<ArtistRepository> { DefaultArtistRepository(get()) }
    single<BrowseRepository> { DefaultBrowseRepository(get()) }
    single<FollowRepository> { DefaultFollowRepository(get()) }
    single<PersonalizationRepository> { DefaultPersonalizationRepository(get()) }
    single<PlayerRepository> { DefaultPlayerRepository(get()) }
    single<PlaylistRepository> { DefaultPlaylistRepository(get(), get()) }
    single<TrackRepository> { DefaultTrackRepository(get()) }
    single<UserRepository> { DefaultUserRepository(get(), get()) }
}