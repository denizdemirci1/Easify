package com.deniz.easify.ui.profile.favorites.topTracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentTopTracksBinding
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksFragment : Fragment() {

    private lateinit var binding: FragmentTopTracksBinding

    private val viewModel by viewModel<TopTracksViewModel>()

    private val args: TopTracksFragmentArgs by navArgs()

    private lateinit var topTracksAdapter: TopTracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_top_tracks, container, false)
        binding = FragmentTopTracksBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle()
        setupTopTracksAdapter()
        setupObservers()
        viewModel.start(args.favorites)
    }

    private fun setToolbarTitle() {
        binding.title.text = getString(R.string.fragment_top_tracks_title,
            args.favorites?.items?.size)
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is TopTracksViewEvent.NotifyDataChanged -> onViewDataChange(event.topTrack)

                is TopTracksViewEvent.OpenFeaturesFragment -> openFeaturesFragment(event.track)

                is TopTracksViewEvent.OpenPlaylistsFragment -> openPlaylistFragment(event.track)
            }
        })
    }

    private fun setupTopTracksAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            topTracksAdapter = TopTracksAdapter(viewModel)
            binding.tracksRecyclerView.adapter = topTracksAdapter
        } else {
            Log.i("TopTracksFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(topTrack: TopTrack) {
        topTracksAdapter.submitList(topTrack.items)
    }

    private fun openFeaturesFragment(track: Track) {
        val action = TopTracksFragmentDirections.actionTopTracksFragmentToFeaturesFragment(track)
        findNavController().navigate(action)
    }

    private fun openPlaylistFragment(track: Track) {
        val action = TopTracksFragmentDirections.actionTopTracksFragmentToPlaylistFragment(track)
        findNavController().navigate(action)
    }
}
