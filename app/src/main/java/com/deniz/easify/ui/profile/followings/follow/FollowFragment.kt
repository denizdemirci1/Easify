package com.deniz.easify.ui.profile.followings.follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.databinding.FragmentFollowBinding
import com.deniz.easify.extension.afterTextChanged
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    private val viewModel by viewModel<FollowViewModel>()

    private lateinit var followAdapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_follow, container, false)
        binding = FragmentFollowBinding.bind(root).apply {
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

    private fun setupListeners() {
        binding.search.setOnFocusChangeListener { _, focused ->
            binding.search.hint = if (!focused) getString(R.string.search) else ""
        }

        binding.search.afterTextChanged { input ->
            viewModel.fetchArtists(input)
        }
    }

    private fun setupObservers() {
        viewModel.artists.observe(this) {
            setupFollowAdapter()
        }

        viewModel.openArtistFragmentEvent.observe(this, EventObserver {
            navigateToArtistFragment(it)
        })
    }

    private fun setupFollowAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            followAdapter = FollowAdapter(viewModel)
            binding.artistsRecyclerView.adapter = followAdapter
        } else {
            Log.i("FollowFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun navigateToArtistFragment(artist: Artist) {
        val action = FollowFragmentDirections.actionFollowFragmentToArtistFragment(artist)
        findNavController().navigate(action)
    }
}
