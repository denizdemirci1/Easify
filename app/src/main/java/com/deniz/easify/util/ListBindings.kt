package com.deniz.easify.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.Item
import com.deniz.easify.ui.profile.favorites.topArtists.TopArtistsAdapter
import com.deniz.easify.ui.profile.favorites.topTracks.TopTracksAdapter
import com.deniz.easify.ui.search.TrackAdapter

/**
 * @User: deniz.demirci
 * @Date: 2019-12-06
 */

/***
 * This file is used to bind list items to the recycler view in layout file.
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Item>) {
    (listView.adapter as TrackAdapter).submitList(items)
}

@BindingAdapter("app:artists")
fun setTopArtists(listView: RecyclerView, items: List<Artist>) {
    (listView.adapter as TopArtistsAdapter).submitList(items)
}

@BindingAdapter("app:tracks")
fun setTopTracks(listView: RecyclerView, items: List<Item>) {
    (listView.adapter as TopTracksAdapter).submitList(items)
}
