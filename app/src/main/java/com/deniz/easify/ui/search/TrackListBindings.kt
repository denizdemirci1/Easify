package com.deniz.easify.ui.search

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Item
import com.deniz.easify.ui.profile.favorites.favoriteDetails.TopTracksAdapter

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Item>) {
    (listView.adapter as TrackAdapter).submitList(items)
}

