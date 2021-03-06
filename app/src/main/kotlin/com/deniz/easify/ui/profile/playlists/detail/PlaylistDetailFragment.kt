package com.deniz.easify.ui.profile.playlists.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentPlaylistDetailBinding
import com.deniz.easify.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistDetailFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistDetailBinding

    private val viewModel by viewModel<PlaylistDetailViewModel>()

    private val args: PlaylistDetailFragmentArgs by navArgs()

    private lateinit var playlistDetailAdapter: PlaylistDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        binding = FragmentPlaylistDetailBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.start(args.playlist, args.editable)
        setupPlaylistDetailAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is PlaylistDetailViewEvent.NotifyDataChanged -> onViewDataChange(event.tracks)

                is PlaylistDetailViewEvent.OpenFeatureFragment -> openFeaturesFragment(event.track)

                is PlaylistDetailViewEvent.ShowError -> showError(event.message)

                is PlaylistDetailViewEvent.ShowSnackBar -> {
                    showSnackbar(getString(
                        R.string.fragment_playlist_detail_removed, event.trackName)
                    )
                }
            }
        })
    }

    private fun setupPlaylistDetailAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            playlistDetailAdapter = PlaylistDetailAdapter(viewModel)
            playlistDetailAdapter.removeListener = { track ->
                viewModel.removeTrack(track)
            }
            binding.tracksRecyclerView.adapter = playlistDetailAdapter
        } else {
            Log.i("PlaylistDetailFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(playlistTracks: ArrayList<PlaylistTracks>) {
        playlistDetailAdapter.submitList(playlistTracks)
    }

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
    }

    private fun openFeaturesFragment(track: Track) {
        if (track.album.images.isNullOrEmpty())
            return

        val action = PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToFeaturesFragment(track)
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
