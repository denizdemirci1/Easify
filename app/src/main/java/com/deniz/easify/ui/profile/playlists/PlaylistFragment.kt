package com.deniz.easify.ui.profile.playlists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentPlaylistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding

    private val viewModel by viewModel<PlaylistViewModel>()

    private lateinit var playlistAdapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_playlist, container, false)
        binding = FragmentPlaylistBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchPlaylists()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.playlists.observe(this) {
            setupPlaylistAdapter()
        }
    }

    private fun setupPlaylistAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            playlistAdapter = PlaylistAdapter(viewModel)
            binding.playlistsRecyclerView.adapter = playlistAdapter
        } else {
            Log.i("SearchFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}