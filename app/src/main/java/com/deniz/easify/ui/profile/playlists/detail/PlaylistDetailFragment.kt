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
import com.deniz.easify.R
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
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.tracks.observe(this) {
            setupPlaylistDetailAdapter()
        }

        viewModel.openTrackEvent.observe(viewLifecycleOwner, EventObserver {
            openFeaturesFragment(it)
        })

        viewModel.showSnackbarMessage.observe(viewLifecycleOwner, EventObserver { trackName ->
            val message = String.format(
                resources.getString(R.string.fragment_playlist_detail_removed),
                trackName)
            showSnackbar(message)
        })
    }

    private fun setupPlaylistDetailAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            playlistDetailAdapter = PlaylistDetailAdapter(viewModel)
            binding.tracksRecyclerView.adapter = playlistDetailAdapter
        } else {
            Log.i("PlaylistDetailFragment", "ViewModel not initialized when attempting to set up adapter.")
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
