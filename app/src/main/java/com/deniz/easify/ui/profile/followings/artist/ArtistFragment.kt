package com.deniz.easify.ui.profile.followings.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.FragmentArtistBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_artist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class ArtistFragment : Fragment() {

    private lateinit var binding: FragmentArtistBinding

    private val viewModel by viewModel<ArtistViewModel>()

    private val args: ArtistFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_artist, container, false)
        binding = FragmentArtistBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.start(args.artist)
        viewModel.fetchFollowedArtists()
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.artist.observe(this, Observer { artist ->
            setupViews(artist)
        })

        viewModel.showSnackbarMessage.observe(this, Observer {
            val snackbarMessage = when (it) {
                ArtistViewModel.Follow.FOLLOW -> {
                    String.format(
                        resources.getString(R.string.fragment_artist_snackbar_followed),
                        args.artist?.name)
                }

                ArtistViewModel.Follow.UNFOLLOW -> {
                    String.format(
                        resources.getString(R.string.fragment_artist_snackbar_unfollowed),
                        args.artist?.name)
                }

                else -> ""
            }

            showSnackbar(snackbarMessage)
        })
    }

    private fun setupViews(artist: Artist) {
        // name
        artistName.text = artist.name

        // follower count
        String.format(
            resources.getString(R.string.fragment_artist_follower_count),
            args.artist!!.followers.total
        ).also { followers.text = it }

        // genre(s)
        var genresText = ""
        for (genre in artist.genres) {
            genresText += "$genre,\n"
        }
        genres.text = if (artist.genres.isEmpty()) "unknown" else genresText.substring(0, genresText.length - 2)
    }

    private fun showFollowButton(show: Boolean) {
        follow.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showUnfollowButton(show: Boolean) {
        unfollow.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setupListeners() {
        follow.setOnClickListener {
            args.artist?.let {
                showFollowButton(false)
                showUnfollowButton(true)
                viewModel.followArtist(it.id)
            }
        }

        unfollow.setOnClickListener {
            args.artist?.let {
                showFollowButton(true)
                showUnfollowButton(false)
                viewModel.unfollowArtist(it.id)
            }
        }
    }

    private fun showSnackbar(message: String) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)

            // add color
            snackbar.view.background = this.context?.getDrawable(R.drawable.bg_snackbar)

            // add margin
            val params = snackbar.view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(24, 24, 24, 24)
            snackbar.view.layoutParams = params

            snackbar.show()
        }
    }
}
