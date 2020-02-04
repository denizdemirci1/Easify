package com.deniz.easify.ui.history

import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.ui.base.BaseListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.databinding.ViewholderHistoryBinding
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class HistoryAdapter(private val viewModel: HistoryViewModel) : BaseListAdapter<History>(
    itemsSame = { old, new -> old.track.id == new.track.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int) : RecyclerView.ViewHolder {
        return HistoryViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> holder.bind(viewModel, getItem(position).track)
        }
    }
}

class HistoryViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderHistoryBinding>(
    binding = ViewholderHistoryBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: HistoryViewModel, track: Track) {
        binding.viewmodel = viewModel
        binding.track = track
        binding.executePendingBindings()
    }
}