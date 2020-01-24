package com.deniz.easify.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.*
import com.deniz.easify.ui.history.HistoryAdapter
import com.deniz.easify.ui.profile.favorites.topArtists.TopArtistsAdapter
import com.deniz.easify.ui.profile.favorites.topTracks.TopTracksAdapter
import com.deniz.easify.ui.profile.followings.FollowedArtistsAdapter
import com.deniz.easify.ui.profile.followings.follow.FollowAdapter
import com.deniz.easify.ui.profile.playlists.PlaylistAdapter
import com.deniz.easify.ui.profile.playlists.detail.PlaylistDetailAdapter
import com.deniz.easify.ui.search.TrackAdapter
import com.deniz.easify.ui.search.features.discover.recommended.RecommendedTracksAdapter

/**
 * @User: deniz.demirci
 * @Date: 2019-12-06
 */

/***
 * This file is used to bind list items to the recycler view in layout file.
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Track>) {
    (listView.adapter as TrackAdapter).submitList(items)
}

@BindingAdapter("app:topArtists")
fun setTopArtists(listView: RecyclerView, items: List<Artist>) {
    (listView.adapter as TopArtistsAdapter).submitList(items)
}

@BindingAdapter("app:tracks")
fun setTopTracks(listView: RecyclerView, items: List<Track>) {
    (listView.adapter as TopTracksAdapter).submitList(items)
}

@BindingAdapter("app:tracks")
fun setRecentlyPlayedTracks(listView: RecyclerView, items: List<History>) {
    (listView.adapter as HistoryAdapter).submitList(items)
}

@BindingAdapter("app:followedArtists")
fun setFollowedArtists(listView: RecyclerView, items: List<Artist>) {
    (listView.adapter as FollowedArtistsAdapter).submitList(items)
}

@BindingAdapter("app:artists")
fun setArtists(listView: RecyclerView, items: List<Artist>) {
    (listView.adapter as FollowAdapter).submitList(items)
}

@BindingAdapter("app:recommendations")
fun setRecommendations(listView: RecyclerView, items: List<Track>) {
    (listView.adapter as RecommendedTracksAdapter).submitList(items)
}

@BindingAdapter("app:playlists")
fun setPlaylists(listView: RecyclerView, items: List<Playlist>) {
    (listView.adapter as PlaylistAdapter).submitList(items)
}

@BindingAdapter("app:playlistTracks")
fun setPlaylistTracks(listView: RecyclerView, items: List<PlaylistTracks>) {
    (listView.adapter as PlaylistDetailAdapter).submitList(items)
}
