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
        setObservers()
    }

    private fun setObservers() {
        viewModel.recommendedTracks.observe(viewLifecycleOwner) {
            setUpTitle(it.tracks.size)
            onViewDataChange(it.tracks)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver { message ->
            showError(message)
        })

        viewModel.openTrackFragmentEvent.observe(viewLifecycleOwner, EventObserver { track ->
            openTrackOnSpotify(track)
        })
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

    private fun showError(message: String) {
        view?.context?.let {
            MaterialDialog(it).show {
                title(R.string.dialog_error_title)
                message(text = message)
                positiveButton(R.string.dialog_ok)
            }
        }
    }

    private fun openTrackOnSpotify(track: Track) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(track.uri)))
    }
}
