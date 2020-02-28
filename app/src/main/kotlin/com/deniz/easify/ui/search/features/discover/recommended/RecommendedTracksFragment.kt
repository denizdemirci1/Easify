package com.deniz.easify.ui.search.features.discover.recommended

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentRecommendedTracksBinding
import com.deniz.easify.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksFragment : Fragment() {

    private lateinit var binding: FragmentRecommendedTracksBinding

    private val viewModel by viewModel<RecommendedTracksViewModel>()

    private val args: RecommendedTracksFragmentArgs by navArgs()

    private lateinit var recommendedTracksAdapter: RecommendedTracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_recommended_tracks, container, false)
        binding = FragmentRecommendedTracksBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecommendedTracksAdapter()
        viewModel.start(args.features)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.createPlaylist.setOnClickListener {
            it.isEnabled = false
            it.alpha = 0.3f
            createPlaylist()
        }
    }

    private fun setObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is RecommendedTracksViewEvent.SetTitle -> setUpTitle(event.size)

                is RecommendedTracksViewEvent.NotifyDataChanged -> onViewDataChange(event.recommendations)

                is RecommendedTracksViewEvent.ShowSnackBar -> showSnackBar(event.isSuccessful)

                is RecommendedTracksViewEvent.ShowError -> showError(event.message)

                is RecommendedTracksViewEvent.OpenTrackOnSpotify -> openTrackOnSpotify(event.track)
            }
        })
    }

    private fun createPlaylist() {
        args.track?.let { track ->
            viewModel.createPlaylist(
                name = getString(R.string.fragment_recommended_tracks_create_playlist_name, track.name),
                description = getString(R.string.fragment_recommended_tracks_create_playlist_desc)
            )
            return
        }

        showError(getString(R.string.dialog_common_error_text))
    }

    private fun setUpTitle(value: Int) {
        binding.title.text = getString(R.string.fragment_recommended_tracks_title, value)
    }

    private fun setUpRecommendedTracksAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            recommendedTracksAdapter = RecommendedTracksAdapter(viewModel)
            binding.tracksRecyclerView.adapter = recommendedTracksAdapter
        } else {
            Log.i("FollowedArtistsFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onViewDataChange(tracks: ArrayList<Track>) {
        recommendedTracksAdapter.submitList(tracks)
    }

    private fun showSnackBar(isSuccessful: Boolean) {
        val message =
            if (isSuccessful)
                getString(R.string.fragment_recommended_tracks_create_playlist_succeeded)
            else
                getString(R.string.fragment_recommended_tracks_create_playlist_failed)

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

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
    }

    private fun openTrackOnSpotify(track: Track) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(track.uri)))
    }
}
