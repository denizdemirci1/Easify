package com.deniz.easify.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentHistoryBinding
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-24
 */

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel by viewModel<HistoryViewModel>()

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHistoryAdapter()
        setupObservers()
        viewModel.fetchRecentlyPlayedSongs()
    }

    private fun setupObservers() {
        viewModel.openTrackEvent.observe(viewLifecycleOwner, EventObserver {
            openFeaturesFragment(it)
        })

        viewModel.openPlaylistsPageEvent.observe(viewLifecycleOwner, EventObserver {
            openPlaylistFragment(it)
        })

        viewModel.historyList.observe(this) {
            onViewDataChange(it)
        }
    }

    private fun setupHistoryAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            historyAdapter = HistoryAdapter(viewModel)
            binding.tracksRecyclerView.adapter = historyAdapter
        } else {
            Log.i("SearchFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(history: ArrayList<History>) {
        historyAdapter.submitList(history)
    }

    private fun openFeaturesFragment(track: Track) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToFeaturesFragment(track)
        findNavController().navigate(action)
    }

    private fun openPlaylistFragment(track: Track) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToPlaylistFragment(track)
        findNavController().navigate(action)
    }
}
