package com.deniz.easify.ui.search.features.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.RecommendationsObject
import com.deniz.easify.databinding.FragmentDiscoverBinding
import com.deniz.easify.extension.onProgressChanged
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding

    val viewModel by viewModel<DiscoverViewModel>()

    private val args: DiscoverFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_discover, container, false)
        binding = FragmentDiscoverBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start(args.features)
        setListeners()
        setObservers()
    }

    /**
     * When user changes progress of a seek bar, set and show the current value
     * on the text view accordingly
     */
    private fun setListeners() {
        binding.danceabilitySeek.onProgressChanged { setDanceability(it) }
        binding.energySeek.onProgressChanged { setEnergy(it) }
        binding.speechinessSeek.onProgressChanged { setSpeechiness(it) }
        binding.acousticnessSeek.onProgressChanged { setAcousticness(it) }
        binding.instrumentalnessSeek.onProgressChanged { setInstrumentalness(it) }
        binding.livenessSeek.onProgressChanged { setLiveness(it) }
        binding.valenceSeek.onProgressChanged { setValence(it) }
        binding.tempoSeek.onProgressChanged { setTempo(it) }

        binding.discover.setOnClickListener {
            val features = FeaturesObject(
                binding.danceabilitySeek.progress.toFloat(),
                binding.energySeek.progress.toFloat(),
                binding.speechinessSeek.progress.toFloat(),
                binding.acousticnessSeek.progress.toFloat(),
                binding.instrumentalnessSeek.progress.toFloat(),
                binding.livenessSeek.progress.toFloat(),
                binding.valenceSeek.progress.toFloat(),
                binding.tempoSeek.progress.toFloat(),
                args.features!!.id
            )
            navigateToRecommendedTracksFragment(features)
        }
    }

    private fun setObservers() {
        viewModel.trackFeatures.observe(viewLifecycleOwner) {
            initializeFeatures(it)
        }
    }

    private fun navigateToRecommendedTracksFragment(features: FeaturesObject) {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToRecommendedTracksFragment(features)
        findNavController().navigate(action)
    }

    //region Features Are Set Here

    /**
     * This function initialize the features for search with the value of
     * selected track from previous fragment
     */
    private fun initializeFeatures(features: FeaturesObject) {
        setDanceability(features.danceability)
        setEnergy(features.energy)
        setSpeechiness(features.speechiness)
        setAcousticness(features.acousticness)
        setInstrumentalness(features.instrumentalness)
        setLiveness(features.liveness)
        setValence(features.valence)
        setTempo(features.tempo)
    }

    private fun setDanceability(value: Float) {
        binding.danceability.text =
            getString(R.string.fragment_features_danceability, (value * 100))
    }

    private fun setEnergy(value: Float) {
        binding.energy.text =
            getString(R.string.fragment_features_energy, (value * 100))
    }

    private fun setSpeechiness(value: Float) {
        binding.speechiness.text =
            getString(R.string.fragment_features_speechiness, (value * 100))
    }

    private fun setAcousticness(value: Float) {
        binding.acousticness.text =
            getString(R.string.fragment_features_acousticness, (value * 100))
    }

    private fun setInstrumentalness(value: Float) {
        binding.instrumentalness.text =
            getString(R.string.fragment_features_instrumentalness, (value * 100))
    }

    private fun setLiveness(value: Float) {
        binding.liveness.text =
            getString(R.string.fragment_features_liveness, (value * 100))
    }

    private fun setValence(value: Float) {
        binding.valence.text =
            getString(R.string.fragment_features_valence, (value * 100))
    }

    private fun setTempo(value: Float) {
        binding.tempo.text = getString(R.string.fragment_features_tempo, value)
    }
    // endregion
}
