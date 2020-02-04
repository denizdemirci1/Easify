package com.deniz.easify.ui.search

import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.ui.base.BaseListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.easify.databinding.ViewholderSearchBinding
import com.deniz.easify.ui.base.BaseViewHolder

/**
 * @User: deniz.demirci
 * @Date: 2020-02-04
 */

/**
 * Class for presenting Search List data in a [RecyclerView], including computing
 * diffs between Lists on a background thread.
 *
 * @see BaseListAdapter
 */
class SearchAdapter(private val viewModel: SearchViewModel) : BaseListAdapter<Track>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to
     * represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param inflater Instantiates a layout XML file into its corresponding View objects.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see BaseListAdapter.onCreateViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int) : RecyclerView.ViewHolder {
        return SearchViewHolder(parent, inflater)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @see BaseListAdapter.onBindViewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }
}

/**
 * Class describes search view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class SearchViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<ViewholderSearchBinding>(
    binding = ViewholderSearchBinding.inflate(inflater, parent, false)
) {
    /**
     * Bind view data binding variables.
     *
     * @param track track to bind.
     */
    fun bind(viewModel: SearchViewModel, track: Track) {
        binding.viewmodel = viewModel
        binding.track = track
        binding.executePendingBindings()
    }

}