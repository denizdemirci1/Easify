package com.deniz.easify.ui.profile.favorites.favoriteDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentFavoriteDetailsBinding
import com.deniz.easify.ui.search.TrackAdapter
import kotlinx.android.synthetic.main.fragment_favorite_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoriteDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteDetailsBinding

    private val viewModel by viewModel<FavoriteDetailsViewModel>()

    private val args: FavoriteDetailsFragmentArgs by navArgs()

    private var topTracksAdapter: TopTracksAdapter? = null
    private var topArtistsAdapter: TopTracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorite_details, container, false)
        binding = FragmentFavoriteDetailsBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        // Set toolbar title
        val toolbarTitle = String.format(resources.getString(R.string.fragment_favorite_details_title), args.favorites?.items?.size, args.type)
        title.text = toolbarTitle

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start(args.favorites)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.track.observe(this, Observer {
            setupAdapter()
        })
    }

    private fun setupAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            topTracksAdapter = TopTracksAdapter(viewModel)
            binding.tracksRecyclerView.adapter = topTracksAdapter
        } else {
            Log.i("FavoriteDetailsFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}