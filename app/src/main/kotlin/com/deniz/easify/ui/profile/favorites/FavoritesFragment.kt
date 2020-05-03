package com.deniz.easify.ui.profile.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.databinding.FragmentFavoritesBinding
import com.deniz.easify.extension.hideKeyboard
import com.deniz.easify.util.EventObserver
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
        private const val KEY_ARTISTS = "artists"
        private const val KEY_TRACKS = "tracks"
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

        // TODO: Change EditTexts to be DropDown Boxes
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
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is FavoritesViewEvent.OpenTopArtists -> openTopArtistsFragment(event.topArtist)

                is FavoritesViewEvent.OpenTopTracks -> openTopTracksFragment(event.topTrack)

                is FavoritesViewEvent.ShowError -> showError(event.message)
            }
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
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
    }

    private fun setupListeners() {
        binding.show.setOnClickListener {
            it.hideKeyboard()

            val term = when (binding.timeRange.text.toString()) {
                longTerm.first -> longTerm.second
                mediumTerm.first -> mediumTerm.second
                shortTerm.first -> shortTerm.second
                else -> longTerm.second
            }

            val amount = when {
                binding.limit.text.toString() == "0" -> {
                    showError(getString(R.string.fragment_favorites_amount_zero_error))
                    return@setOnClickListener
                }
                binding.limit.text.toString().isNotEmpty() -> Integer.parseInt(binding.limit.text.toString())
                else -> null
            }

            when {
                binding.type.text.toString() == KEY_ARTISTS -> fetchTopArtists(term, amount)
                binding.type.text.toString() == KEY_TRACKS -> fetchTopTracks(term, amount)
                else -> showError(resources.getString(R.string.fragment_favorites_type_empty_error))
            }
        }

        binding.type.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf(KEY_ARTISTS, KEY_TRACKS)) { _, _, text ->
                        binding.type.setText(text)
                    }
                }
            }
        }

        binding.timeRange.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show {
                    listItems(items = listOf(longTerm.first, mediumTerm.first, shortTerm.first)) { _, _, text ->
                        binding.timeRange.setText(text)
                    }
                }
            }
        }
    }

    private fun fetchTopArtists(term: String, amount: Int?) {
        viewModel.fetchTopArtists(
            KEY_ARTISTS,
            term,
            amount)
    }

    private fun fetchTopTracks(term: String, amount: Int?) {
        viewModel.fetchTopTracks(
            KEY_TRACKS,
            term,
            amount)
    }
}
