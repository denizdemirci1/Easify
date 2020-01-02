package com.deniz.easify.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentTrackBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-11-26
 */

class TrackFragment : Fragment() {

    private lateinit var binding: FragmentTrackBinding

    private val viewModel by viewModel<TrackViewModel>()

    private val args: TrackFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_track, container, false)
        binding = FragmentTrackBinding.bind(root).apply {
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
        binding.play.setOnClickListener {
            viewModel.playTrack()
        }

        binding.pause.setOnClickListener {
            viewModel.pauseTrack()
        }
    }

    private fun setObservers() {
    }
}
