package com.deniz.easify.ui.profile.favorites.topTracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderTopTracksBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class TopTracksAdapter(private val viewModel: TopTracksViewModel) : BaseListAdapter<Track>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return TopTracksViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopTracksViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class TopTracksViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderTopTracksBinding>(
    binding = ViewholderTopTracksBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: TopTracksViewModel, track: Track) {
        binding.viewmodel = viewModel
        binding.track = track
        binding.executePendingBindings()
    }
}
