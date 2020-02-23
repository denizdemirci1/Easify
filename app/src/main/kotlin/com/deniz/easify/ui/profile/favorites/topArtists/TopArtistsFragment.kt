package com.deniz.easify.ui.profile.favorites.topArtists

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
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.databinding.FragmentTopArtistsBinding
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class TopArtistsFragment : Fragment() {

    private lateinit var binding: FragmentTopArtistsBinding

    private val viewModel by viewModel<TopArtistsViewModel>()

    private val args: TopArtistsFragmentArgs by navArgs()

    private lateinit var topArtistsAdapter: TopArtistsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_top_artists, container, false)
        binding = FragmentTopArtistsBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle()
        setupTopArtistsAdapter()
        setupObservers()
        viewModel.start(args.favorites)
    }

    private fun setToolbarTitle() {
        binding.title.text =
            getString(R.string.fragment_top_artists_title, args.favorites?.items?.size)
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is TopArtistsViewEvent.NotifyDataChanged -> onViewDataChange(event.topArtist)

                is TopArtistsViewEvent.OpenArtistFragment -> navigateToArtistFragment(event.artist)
            }
        })
    }

    private fun setupTopArtistsAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            topArtistsAdapter = TopArtistsAdapter(viewModel)
            binding.tracksRecyclerView.adapter = topArtistsAdapter
        } else {
            Log.i("TopArtistsFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(topArtist: TopArtist) {
        topArtistsAdapter.submitList(topArtist.items)
    }

    private fun navigateToArtistFragment(artist: Artist) {
        val action = TopArtistsFragmentDirections.actionTopArtistsFragmentToArtistFragment(artist)
        findNavController().navigate(action)
    }
}
