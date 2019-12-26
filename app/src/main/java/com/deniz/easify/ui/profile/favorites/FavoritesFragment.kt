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
import com.deniz.easify.util.EventObserver
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

    companion object {
        private const val artists = "artists"
        private const val tracks = "tracks"
        private val longTerm = Pair("Several Years", "long_term")
        private val mediumTerm = Pair("Last 6 Months", "medium_term")
        private val shortTerm = Pair("Last 4 Weeks", "short_term")
    }

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

        viewModel.topArtist.observe(this, EventObserver {
            openTopArtistsFragment(it)
        })

        viewModel.topTracks.observe(this, EventObserver {
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

            val term = when (timeRange.text.toString()) {
                longTerm.first -> longTerm.second
                mediumTerm.first -> mediumTerm.second
                shortTerm.first -> shortTerm.second
                else -> longTerm.second
            }

            val amount = when {
                limit.text.toString().isNotEmpty() -> Integer.parseInt(limit.text.toString())
                limit.text.toString() == "0" -> null
                else -> null
            }

            when {
                type.text.toString() == artists -> fetchTopArtists(term, amount)
                type.text.toString() == tracks -> fetchTopTracks(term, amount)
                else -> showError(resources.getString(R.string.fragment_favorites_type_empty_error))
            }
        }

        type.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf(artists, tracks)) { _, _, text ->
                        this@FavoritesFragment.type.setText(text)
                    }
                }
            }
        }

        timeRange.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf(longTerm.first, mediumTerm.first, shortTerm.first)) { _, _, text ->
                        this@FavoritesFragment.timeRange.setText(text)
                    }
                }
            }
        }
    }

    private fun fetchTopArtists(term: String, amount: Int?) {
        viewModel.fetchTopArtists(
            artists,
            term,
            amount)
    }

    private fun fetchTopTracks(term: String, amount: Int?) {
        viewModel.fetchTopTracks(
            tracks,
            term,
            amount)
    }
}
