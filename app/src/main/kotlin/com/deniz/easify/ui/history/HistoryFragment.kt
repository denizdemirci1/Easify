package com.deniz.easify.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
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
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is HistoryViewEvent.OpenFeaturesFragment -> openFeaturesFragment(event.track)

                is HistoryViewEvent.OpenPlaylistsFragment -> openPlaylistFragment(event.track)

                is HistoryViewEvent.NotifyDataChanged -> onViewDataChange(event.historyList)

                is HistoryViewEvent.ShowError -> showError(event.message)
            }
        })
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

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
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
