package com.deniz.easify.ui.search.features.discover.recommended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.ViewholderRecommendedTracksBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class RecommendedTracksAdapter(private val viewModel: RecommendedTracksViewModel) : BaseListAdapter<Track>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return RecommendedTracksViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecommendedTracksViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class RecommendedTracksViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderRecommendedTracksBinding>(
    binding = ViewholderRecommendedTracksBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: RecommendedTracksViewModel, track: Track) {
        binding.viewmodel = viewModel
        binding.track = track
        binding.executePendingBindings()
    }
}
