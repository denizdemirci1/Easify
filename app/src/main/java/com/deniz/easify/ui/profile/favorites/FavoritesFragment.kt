package com.deniz.easify.ui.profile.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.databinding.FragmentFavoritesBinding
import com.deniz.easify.extension.hideKeyboard
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

/**
 * Where users can either search for their Top Tracks and navigate to TopTracksFragment or
 * search for their Top Artists and navigate to TopArtistsFragment
 */
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel by viewModel<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        binding = FragmentFavoritesBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {

        viewModel.topArtist.observe(this, Observer {
            openTopArtistsFragment(it)
        })

        viewModel.topTracks.observe(this, Observer {
            openTopTracksFragment(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            showError(it)
        })
    }

    private fun openTopArtistsFragment(topArtist: TopArtist?) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToTopArtistsFragment(topArtist)
        findNavController().navigate(action)
    }

    private fun openTopTracksFragment(topTrack: TopTrack?) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToTopTracksFragment(topTrack)
        findNavController().navigate(action)
    }

    private fun showError(message: String) {
        this.context.let {
            MaterialDialog(it!!).show {
                title(R.string.dialog_error_title)
                message(text = message)
                positiveButton(R.string.dialog_ok)
            }
        }
    }

    private fun setupListeners() {
        show.setOnClickListener {
            it.hideKeyboard()
            if (limit.text.toString() != "0" && limit.text.toString().isNotEmpty()) {
                if (type.text.toString() == "artists") {
                    fetchTopArtists()
                } else if (type.text.toString() == "tracks") {
                    fetchTopTracks()
                }
            }
        }

        type.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf("artists", "tracks")) { _, _, text ->
                        this@FavoritesFragment.type.setText(text)
                    }
                }
            }
        }

        timeRange.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf("long_term", "medium_term", "short_term")) { _, _, text ->
                        this@FavoritesFragment.timeRange.setText(text)
                    }
                }
            }
        }
    }

    private fun fetchTopArtists() {
        viewModel.fetchTopArtists(
            "artists",
            timeRange.text.toString(),
            Integer.parseInt(limit.text.toString()))
    }

    private fun fetchTopTracks() {
        viewModel.fetchTopTracks(
            "tracks",
            timeRange.text.toString(),
            Integer.parseInt(limit.text.toString()))
    }
}
