package com.deniz.easify.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderHistoryBinding

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

/**
 * Adapter for the tracks list. Has a reference to the [HistoryViewModel] to send actions back to it.
 */
class HistoryAdapter(private val viewModel: HistoryViewModel) :
    ListAdapter<History, HistoryAdapter.ViewHolder>(TracksDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = getItem(position).track

        holder.bind(viewModel, track)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewholderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HistoryViewModel, track: Track) {
            binding.viewmodel = viewModel
            binding.track = track
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderHistoryBinding.inflate(layoutInflater, parent, false)

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
class TracksDiffCallback : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.track.id == newItem.track.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }
}
