package com.deniz.easify.ui.profile.followings

import com.deniz.easify.ui.base.BaseListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.ViewholderFollowedArtistsBinding
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class FollowedArtistsAdapter(private val viewModel: FollowedArtistsViewModel) : BaseListAdapter<Artist>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int) : RecyclerView.ViewHolder {
        return FollowedArtistViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FollowedArtistViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class FollowedArtistViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderFollowedArtistsBinding>(
    binding = ViewholderFollowedArtistsBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: FollowedArtistsViewModel, artist: Artist) {
        binding.viewmodel = viewModel
        binding.artist = artist
        binding.executePendingBindings()
    }
}
