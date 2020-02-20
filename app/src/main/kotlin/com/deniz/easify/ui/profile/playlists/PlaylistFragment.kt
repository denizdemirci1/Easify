package com.deniz.easify.ui.profile.playlists

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
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.databinding.FragmentPlaylistBinding
import com.deniz.easify.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding

    private val viewModel by viewModel<PlaylistViewModel>()

    private val args: PlaylistFragmentArgs by navArgs()

    private lateinit var playlistsAdapter: PlaylistsAdapter

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
        viewModel.start(args.track)
        setupPlaylistAdapter()
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.playlists.observe(viewLifecycleOwner) {
            onViewDataChange(it)
        }

        viewModel.reason.observe(viewLifecycleOwner) {
            binding.title.text = when (it) {
                PlaylistViewModel.Reason.ADD -> getString(R.string.fragment_playlist_title_add)
                PlaylistViewModel.Reason.SEE -> getString(R.string.fragment_playlist_title_see)
            }
        }

        viewModel.playlistClickedEvent.observe(viewLifecycleOwner, EventObserver {
            when (viewModel.reason.value) {
                PlaylistViewModel.Reason.SEE -> openPlaylistDetailFragment(it.first, it.second)
                PlaylistViewModel.Reason.ADD -> viewModel.fetchPlaylistTracks(args.track!!, it.first)
            }
        })

        viewModel.trackAddingResult.observe(viewLifecycleOwner, EventObserver {
            if (it.second)
                showSnackbar(
                    getString(R.string.fragment_playlist_track_added_to_playlist,
                        it.first.first,
                        it.first.second))
            else
                showSnackbar(
                    getString(R.string.fragment_playlist_track_already_exists_in_playlist,
                        it.first.first,
                        it.first.second))
        })
    }

    private fun setupListeners() {
        binding.add.setOnClickListener {
            openCreatePlaylistFragment()
        }
    }

    private fun setupPlaylistAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            playlistsAdapter = PlaylistsAdapter(viewModel)
            binding.playlistsRecyclerView.adapter = playlistsAdapter
        } else {
            Log.i("PlaylistFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(playlists: ArrayList<Playlist>) {
        playlistsAdapter.submitList(playlists)
    }

    private fun openPlaylistDetailFragment(playlist: Playlist, editable: Boolean) {
        val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlist, editable)
        findNavController().navigate(action)
    }

    private fun openCreatePlaylistFragment() {
        val action = PlaylistFragmentDirections.actionPlaylistFragmentToCreatePlaylistFragment()
        findNavController().navigate(action)
    }

    private fun showSnackbar(message: String) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)

            // add color
            snackbar.view.background = this.context?.getDrawable(R.drawable.bg_snackbar)

            // add margin
            val params = snackbar.view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(24, 24, 24, 24)
            snackbar.view.layoutParams = params

            snackbar.show()
        }
    }
}
