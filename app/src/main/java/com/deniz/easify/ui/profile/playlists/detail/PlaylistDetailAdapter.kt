package com.deniz.easify.ui.profile.playlists.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.databinding.ViewholderPlaylistDetailBinding

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistDetailAdapter(private val viewModel: PlaylistDetailViewModel) :
    ListAdapter<PlaylistTracks, PlaylistDetailAdapter.ViewHolder>(PlaylistDetailDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = getItem(position)

        holder.bind(viewModel, track)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderPlaylistDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PlaylistDetailViewModel, playlistTracks: PlaylistTracks) {
            binding.viewmodel = viewModel
            binding.track = playlistTracks.track
            binding.remove.setOnClickListener {
                binding.trackLayout.visibility = View.GONE
                viewModel.removeTrack(playlistTracks.track)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderPlaylistDetailBinding.inflate(layoutInflater, parent, false)

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
class PlaylistDetailDiffCallback : DiffUtil.ItemCallback<PlaylistTracks>() {
    override fun areItemsTheSame(oldItem: PlaylistTracks, newItem: PlaylistTracks): Boolean {
        return oldItem.track.id == newItem.track.id
    }

    override fun areContentsTheSame(oldItem: PlaylistTracks, newItem: PlaylistTracks): Boolean {
        return oldItem == newItem
    }
}