package com.deniz.easify.ui.profile.favorites.topArtists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.ViewholderTopArtistsBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class TopArtistsAdapter(private val viewModel: TopArtistsViewModel) : BaseListAdapter<Artist>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return TopArtistViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopArtistViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class TopArtistViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderTopArtistsBinding>(
    binding = ViewholderTopArtistsBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: TopArtistsViewModel, artist: Artist) {
        binding.viewmodel = viewModel
        binding.artist = artist
        binding.executePendingBindings()
    }
}
