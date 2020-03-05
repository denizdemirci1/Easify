package com.deniz.easify.ui.profile.playlists

import android.content.Intent
import android.net.Uri
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
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is PlaylistViewEvent.InitUI -> {
                    when (event.reason) {
                        PlaylistViewModel.Reason.ADD -> {
                            binding.title.text = getString(R.string.fragment_playlist_title_add)
                            binding.add.visibility = View.GONE
                        }
                        PlaylistViewModel.Reason.SEE -> {
                            binding.title.text = getString(R.string.fragment_playlist_title_see)
                        }
                    }
                }

                is PlaylistViewEvent.NotifyDataChanged -> onViewDataChange(event.playlists)

                is PlaylistViewEvent.TrackAddingSucceeded -> {
                    showSnackbar(
                        getString(
                            R.string.fragment_playlist_track_added_to_playlist,
                            event.trackName,
                            event.playlistName
                        )
                    )
                }

                is PlaylistViewEvent.TrackAddingFailed -> {
                    showSnackbar(
                        getString(
                            R.string.fragment_playlist_track_already_exists_in_playlist,
                            event.trackName,
                            event.playlistName
                        )
                    )
                }

                is PlaylistViewEvent.OpenPlaylistDetail -> {
                    openPlaylistDetailFragment(event.playlist, event.isEditable)
                }

                is PlaylistViewEvent.OpenPlaylistOnSpotify -> {
                    openPlaylistOnSpotify(event.playlist)
                }

                is PlaylistViewEvent.FetchPlaylistTracks -> {
                    viewModel.fetchPlaylistTracks(args.track!!, event.playlist)
                }
            }
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

    private fun openPlaylistOnSpotify(playlist: Playlist) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playlist.uri)))
    }
}
