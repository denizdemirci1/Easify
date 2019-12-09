package com.deniz.easify.ui.profile.favorites.topTracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Item
import com.deniz.easify.databinding.ViewholderTopTracksBinding

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksAdapter(private val viewModel: TopTracksViewModel) :
    ListAdapter<Item, TopTracksAdapter.ViewHolder>(TopTracksDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(viewModel, artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderTopTracksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: TopTracksViewModel, track: Item) {
            binding.viewmodel = viewModel
            binding.track = track
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderTopTracksBinding.inflate(layoutInflater, parent, false)

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
class TopTracksDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
