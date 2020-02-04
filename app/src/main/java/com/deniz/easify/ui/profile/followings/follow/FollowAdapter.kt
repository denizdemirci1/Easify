package com.deniz.easify.ui.profile.followings.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.ViewholderFollowBinding
import com.deniz.easify.ui.base.BaseListAdapter
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

class FollowAdapter(private val viewModel: FollowViewModel) : BaseListAdapter<Artist>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): RecyclerView.ViewHolder {
        return FollowViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FollowViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

class FollowViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderFollowBinding>(
    binding = ViewholderFollowBinding.inflate(inflater, parent, false)
) {

    fun bind(viewModel: FollowViewModel, artist: Artist) {
        binding.viewmodel = viewModel
        binding.artist = artist
        binding.executePendingBindings()
    }
}
