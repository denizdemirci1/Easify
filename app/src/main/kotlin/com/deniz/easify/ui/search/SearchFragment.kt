package com.deniz.easify.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentSearchBinding
import com.deniz.easify.extension.hideKeyboard
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModel<SearchViewModel>()

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
        setupTrackAdapter()
    }

    private fun setupTrackAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            searchAdapter = SearchAdapter(viewModel)
            binding.tracksRecyclerView.adapter = searchAdapter
        } else {
            Log.i("SearchFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupListeners() {
        binding.clear.setOnClickListener {
            binding.search.setText("")
            binding.search.hint = ""
        }
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is SearchViewEvent.OpenTrack -> openFeaturesFragment(event.track)

                is SearchViewEvent.OpenPlaylistPage -> openPlaylistFragment(event.track)

                is SearchViewEvent.NotifyDataChanged -> onViewDataChange(event.trackList)

                is SearchViewEvent.ShowError -> showError(event.message)
            }
        })
    }

    private fun onViewDataChange(tracks: ArrayList<Track>) {
        searchAdapter.submitList(tracks)
    }

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
    }

    private fun openFeaturesFragment(track: Track) {
        val action = SearchFragmentDirections.actionSearchFragmentToFeaturesFragment(track)
        findNavController().navigate(action)
    }

    private fun openPlaylistFragment(track: Track) {
        view?.hideKeyboard()
        val action = SearchFragmentDirections.actionSearchFragmentToPlaylistFragment(track)
        findNavController().navigate(action)
    }
}
