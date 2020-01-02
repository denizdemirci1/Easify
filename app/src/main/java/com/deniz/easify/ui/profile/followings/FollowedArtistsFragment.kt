package com.deniz.easify.ui.profile.followings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.FragmentFollowedArtistsBinding
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowedArtistsFragment : Fragment() {

    private lateinit var binding: FragmentFollowedArtistsBinding

    private val viewModel by viewModel<FollowedArtistsViewModel>()

    private var followedArtistsAdapter: FollowedArtistsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_followed_artists, container, false)
        binding = FragmentFollowedArtistsBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchFollowedArtists()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.artists.observe(this) {
            setupFollowedArtistsAdapter()
        }

        viewModel.errorMessage.observe(this) {
            showError(it)
        }

        viewModel.openArtistFragmentEvent.observe(this, EventObserver {
            navigateToArtistFragment(it)
        })
    }

    private fun setupFollowedArtistsAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            followedArtistsAdapter = FollowedArtistsAdapter(viewModel)
            binding.artistsRecyclerView.adapter = followedArtistsAdapter
        } else {
            Log.i("FollowedArtistsFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setListeners() {
        binding.add.setOnClickListener {
            navigateToFollowFragment()
        }
    }

    private fun navigateToFollowFragment() {
        val action = FollowedArtistsFragmentDirections.actionFollowedArtistsFragmentToFollowFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArtistFragment(artist: Artist) {
        val action = FollowedArtistsFragmentDirections.actionFollowedArtistsFragmentToArtistFragment(artist)
        findNavController().navigate(action)
    }

    private fun showError(message: String) {
        view?.context?.let {
            MaterialDialog(it).show {
                title(R.string.dialog_error_title)
                message(text = message)
                positiveButton(R.string.dialog_ok)
            }
        }
    }
}
