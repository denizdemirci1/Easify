package com.deniz.easify.ui.profile.favorites.topTracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentTopTracksBinding
import kotlinx.android.synthetic.main.fragment_top_artists.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksFragment : Fragment() {

    private lateinit var binding: FragmentTopTracksBinding

    private val viewModel by viewModel<TopTracksViewModel>()

    private val args: TopTracksFragmentArgs by navArgs()

    private var topTracksAdapter: TopTracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_top_tracks, container, false)
        binding = FragmentTopTracksBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set toolbar title
        val toolbarTitle = String.format(
            resources.getString(R.string.fragment_top_tracks_title),
            args.favorites?.items?.size
        )
        title.text = toolbarTitle

        viewModel.start(args.favorites)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.topTrack.observe(this, Observer {
            setupTopTracksAdapter()
        })
    }

    private fun setupTopTracksAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            topTracksAdapter = TopTracksAdapter(viewModel)
            binding.tracksRecyclerView.adapter = topTracksAdapter
        } else {
            Log.i("TopTracksFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}