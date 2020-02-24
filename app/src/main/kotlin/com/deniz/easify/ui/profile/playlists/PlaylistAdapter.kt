package com.deniz.easify.ui.profile.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.databinding.ViewholderPlaylistBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class PlaylistsAdapter(private val viewModel: PlaylistViewModel) : BaseListAdapter<Playlist>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return PlaylistViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaylistViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class PlaylistViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderPlaylistBinding>(
    binding = ViewholderPlaylistBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: PlaylistViewModel, playlist: Playlist) {
        binding.viewmodel = viewModel
        binding.playlist = playlist
        binding.executePendingBindings()
    }
}
