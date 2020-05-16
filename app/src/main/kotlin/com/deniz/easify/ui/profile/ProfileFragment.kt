package com.deniz.easify.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class ProfileFragment : Fragment() {

    companion object {
        private const val APP_URL =
            "https://play.google.com/store/apps/details?id=com.deniz.easify"
        private const val APP_URI = "market://details?id=com.deniz.easify"
    }

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
        viewModel.fetchUser()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            setupUserInfo(user)
        }
    }

    private fun setupListeners() {
        binding.favorites.setOnClickListener {
            openFavoritesFragment()
        }

        binding.followedArtists.setOnClickListener {
            openFollowedArtistsFragment()
        }

        binding.playlists.setOnClickListener {
            openPlaylistsFragment()
        }

        binding.rateLayout.setOnClickListener {
            openGooglePlayPage()
        }
    }

    private fun setupUserInfo(user: User) {
        // Profile Image
        view?.context?.let { context ->
            if (user.images.isNotEmpty())
                Glide.with(context).load(user.images[0].url).into(binding.profilePicture)
        }

        // Follower Count
        binding.followerCount.text =
            getString(R.string.fragment_artist_follower_count, user.followers.total)
    }

    private fun openFavoritesFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment()
        findNavController().navigate(action)
    }

    private fun openFollowedArtistsFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFollowedArtistsFragment()
        findNavController().navigate(action)
    }

    private fun openPlaylistsFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToPlaylistFragment(track = null)
        findNavController().navigate(action)
    }

    private fun openGooglePlayPage() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(APP_URI)))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(APP_URL)))
        }
    }
}
