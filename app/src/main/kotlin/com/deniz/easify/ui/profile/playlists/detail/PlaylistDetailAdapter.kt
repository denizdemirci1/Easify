package com.deniz.easify.ui.profile.playlists.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderPlaylistDetailBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

// TODO: Can be good for when statement on onBindViewHolder

class PlaylistDetailAdapter(
    private val viewModel: PlaylistDetailViewModel,
    var removeListener: ((Track) -> Unit)? = null) : BaseListAdapter<PlaylistTracks>(
    itemsSame = { old, new -> old.track.id == new.track.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return PlaylistDetailViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaylistDetailViewHolder -> holder.bind(viewModel, getItem(position).track, removeListener)
        }
    }
}

class PlaylistDetailViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderPlaylistDetailBinding>(
    binding = ViewholderPlaylistDetailBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: PlaylistDetailViewModel, track: Track, removeListener: ((Track) -> Unit)?) {
        binding.viewmodel = viewModel
        binding.track = track
        binding.remove.setOnClickListener {
            removeListener?.invoke(track) }
        binding.executePendingBindings()
    }
}
