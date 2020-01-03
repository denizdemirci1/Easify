package com.deniz.easify.ui.search.features.discover.recommended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderRecommendedTracksBinding

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksAdapter(private val viewModel: RecommendedTracksViewModel) :
    ListAdapter<Track, RecommendedTracksAdapter.ViewHolder>(RecommendedTrackDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(viewModel, artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderRecommendedTracksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: RecommendedTracksViewModel, track: Track) {
            binding.viewmodel = viewModel
            binding.track = track
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderRecommendedTracksBinding.inflate(layoutInflater, parent, false)

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
class RecommendedTrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
