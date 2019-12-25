package com.deniz.easify.ui.profile.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.ViewholderFollowedArtistsBinding

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowedArtistsAdapter(private val viewModel: FollowedArtistsViewModel) :
    ListAdapter<Artist, FollowedArtistsAdapter.ViewHolder>(FollowedArtistsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(viewModel, artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderFollowedArtistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FollowedArtistsViewModel, artist: Artist) {
            binding.viewmodel = viewModel
            binding.artist = artist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderFollowedArtistsBinding.inflate(layoutInflater, parent, false)

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
class FollowedArtistsDiffCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}