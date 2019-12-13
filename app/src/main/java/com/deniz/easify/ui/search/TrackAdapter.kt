package com.deniz.easify.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderTrackBinding

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

/**
 * Adapter for the tracks list. Has a reference to the [SearchViewModel] to send actions back to it.
 */
class TrackAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<Track, TrackAdapter.ViewHolder>(TracksDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, track: Track) {
            binding.viewmodel = viewModel
            binding.track = track
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderTrackBinding.inflate(layoutInflater, parent, false)

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
class TracksDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
