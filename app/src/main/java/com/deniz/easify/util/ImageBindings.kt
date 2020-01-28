package com.deniz.easify.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Playlist

/**
 * @User: deniz.demirci
 * @Date: 2019-11-20
 */

/**
 * This file is used to bind images to an image view in layout file.
 */

@BindingAdapter("imageUrl")
fun loadTrackImage(view: AppCompatImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("playlistImage")
fun loadPlaylistImage(view: AppCompatImageView, playlist: Playlist) {
    if (playlist.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_music_note)
    } else {
        Glide.with(view.context).load(playlist.images[playlist.images.size - 1].url).into(view)
    }
}
