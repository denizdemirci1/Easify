package com.deniz.easify.util.bindings

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2019-11-20
 */

/**
 * This file is used to bind images to an image view in layout file.
 */

@BindingAdapter("trackImage")
fun loadTrackImage(view: AppCompatImageView, track: Track) {
    if (track.album.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_music_note)
    } else {
        Glide.with(view.context).load(track.album.images[track.album.images.size - 1].url).into(view)
    }
}

@BindingAdapter("trackImageBig")
fun loadBigTrackImage(view: AppCompatImageView, track: Track) {
    if (track.album.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_music_note)
    } else {
        Glide.with(view.context).load(track.album.images[0].url).into(view)
    }
}

@BindingAdapter("artistImage")
fun loadArtistImage(view: AppCompatImageView, artist: Artist) {
    if (artist.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_person)
    } else {
        Glide.with(view.context).load(artist.images[artist.images.size - 1].url).into(view)
    }
}

@BindingAdapter("artistImageBig")
fun loadBigArtistImage(view: AppCompatImageView, artist: Artist) {
    if (artist.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_person)
    } else {
        Glide.with(view.context).load(artist.images[0].url).into(view)
    }
}

@BindingAdapter("playlistImage")
fun loadPlaylistImage(view: AppCompatImageView, playlist: Playlist) {
    if (playlist.images.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_music_note)
    } else {
        Glide.with(view.context).load(playlist.images[playlist.images.size - 1].url).into(view)
    }
}
