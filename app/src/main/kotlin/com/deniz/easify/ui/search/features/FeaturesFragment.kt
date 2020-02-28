package com.deniz.easify.ui.search.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.databinding.FragmentFeaturesBinding
import com.deniz.easify.util.EventObserver
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
            openDiscoverFragment(viewModel.trackFeatures.value)
        }
    }

    private fun setObservers() {
        viewModel.trackFeatures.observe(viewLifecycleOwner) {
            setFeatures(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })
    }

    private fun openDiscoverFragment(trackFeatures: FeaturesObject?) {
        val action = FeaturesFragmentDirections.actionFeaturesFragmentToDiscoverFragment(trackFeatures, args.track)
        findNavController().navigate(action)
    }

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
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
