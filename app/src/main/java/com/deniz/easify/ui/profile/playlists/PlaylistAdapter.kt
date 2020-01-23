package com.deniz.easify.ui.profile.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.databinding.ViewholderPlaylistBinding

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistAdapter(private val viewModel: PlaylistViewModel) :
    ListAdapter<Playlist, PlaylistAdapter.ViewHolder>(FollowedArtistsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = getItem(position)

        holder.bind(viewModel, playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PlaylistViewModel, playlist: Playlist) {
            binding.viewmodel = viewModel
            binding.playlist = playlist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderPlaylistBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class FollowedArtistsDiffCallback : DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }
}