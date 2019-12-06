package com.deniz.easify.ui.profile.favorites.topArtists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.ViewholderTopArtistsBinding

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class TopArtistsAdapter(private val viewModel: TopArtistsViewModel) :
    ListAdapter<Artist, TopArtistsAdapter.ViewHolder>(TracksDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(viewModel, artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderTopArtistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: TopArtistsViewModel, artist: Artist) {
            binding.viewmodel = viewModel
            binding.artist = artist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderTopArtistsBinding.inflate(layoutInflater, parent, false)

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
class TracksDiffCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}
