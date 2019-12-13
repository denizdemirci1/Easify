package com.deniz.easify.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentSearchBinding
import com.deniz.easify.extension.afterTextChanged
import com.deniz.easify.util.EventObserver
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModel<SearchViewModel>()

    private lateinit var trackAdapter: TrackAdapter

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

        setupNavigation()
        setupListeners()
        setupObservers()
    }

    private fun setupTrackAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            trackAdapter = TrackAdapter(viewModel)
            binding.tracksRecyclerView.adapter = trackAdapter
        } else {
            Log.i("SearchFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupNavigation() {
        viewModel.openTrackEvent.observe(this, EventObserver {
            openTrackDetails(it)
        })
    }

    private fun setupListeners() {
        search.setOnFocusChangeListener { _, focused ->
            search.hint = if (!focused) getString(R.string.search) else ""
        }

        search.afterTextChanged { input ->
            viewModel.fetchSongs(input)
        }
    }

    private fun setupObservers() {
        viewModel.trackList.observe(this, Observer {
            setupTrackAdapter()
        })
    }

    private fun openTrackDetails(track: Track) {
        val action = SearchFragmentDirections.actionSearchFragmentToTrackFragment(track)
        findNavController().navigate(action)
    }
}
