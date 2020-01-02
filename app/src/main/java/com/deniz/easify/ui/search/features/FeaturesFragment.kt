package com.deniz.easify.ui.search.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.databinding.FragmentFeaturesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-31
 */

class FeaturesFragment : Fragment() {

    private lateinit var binding: FragmentFeaturesBinding

    private val viewModel by viewModel<FeaturesViewModel>()

    private val args: FeaturesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_features, container, false)
        binding = FragmentFeaturesBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start(args.track)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.search.setOnClickListener {
            // navigate to Search Screen
        }
    }

    private fun setObservers() {
        viewModel.trackFeatures.observe(this) {
            setFeatures(it)
        }
    }

    //region Features Are Set Here

    // This function only sets features texts. SeekBars are set in xml file
    private fun setFeatures(features: FeaturesObject) {
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
        val text = String.format(
            resources.getString(R.string.fragment_features_danceability),
            value)
        binding.danceability.text = text
    }

    private fun setEnergy(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_energy),
            value)
        binding.energy.text = text
    }

    private fun setSpeechiness(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_speechiness),
            value)
        binding.speechiness.text = text
    }

    private fun setAcousticness(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_acousticness),
            value)
        binding.acousticness.text = text
    }

    private fun setInstrumentalness(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_instrumentalness),
            value)
        binding.instrumentalness.text = text
    }

    private fun setLiveness(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_liveness),
            value)
        binding.liveness.text = text
    }

    private fun setValence(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_valence),
            value)
        binding.valence.text = text
    }

    private fun setTempo(value: Float) {
        val text = String.format(
            resources.getString(R.string.fragment_features_tempo),
            value)
        binding.tempo.text = text
    }
    // endregion
}
