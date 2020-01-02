package com.deniz.easify.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class ProfileFragment : Fragment() {

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
    }

    private fun setupObservers() {
        viewModel.user.observe(this) { user ->
            setupUserFollowerCount(user)
        }
    }

    private fun setupListeners() {
        binding.favorites.setOnClickListener {
            openFavoritesFragment()
        }

        binding.followedArtists.setOnClickListener {
            openFollowedArtistsFragment()
        }
    }

    private fun setupUserFollowerCount(user: User) {
        String.format(
            resources.getString(R.string.fragment_artist_follower_count),
            user.followers.total
        ).also { binding.followerCount.text = it }
    }

    private fun openFavoritesFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment()
        findNavController().navigate(action)
    }

    private fun openFollowedArtistsFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFollowedArtistsFragment()
        findNavController().navigate(action)
    }
}
