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
        viewModel.start(args.track)
        viewModel.fetchPlaylists()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.playlists.observe(this) {
            setupPlaylistAdapter()
        }

        viewModel.playlistClickedEvent.observe(this, EventObserver {
            when (viewModel.reason.value) {
                PlaylistViewModel.Reason.SEE -> openPlaylistDetailFragment(it.first, it.second)
                PlaylistViewModel.Reason.ADD -> viewModel.addTrackToPlaylist(args.track!!, it.first)
            }
        })

        viewModel.showSnackbarMessage.observe(this, EventObserver {
            val message = String.format(
                resources.getString(R.string.fragment_playlist_track_added_to_playlist),
                it.second,
                it.first)
            showSnackbar(message)
        })
    }

    private fun setupPlaylistAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            playlistAdapter = PlaylistAdapter(viewModel)
            binding.playlistsRecyclerView.adapter = playlistAdapter
        } else {
            Log.i("PlaylistFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun openPlaylistDetailFragment(playlist: Playlist, editable: Boolean) {
        val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlist, editable)
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
