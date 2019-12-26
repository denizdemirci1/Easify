package com.deniz.easify.ui.profile.followings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentFollowedArtistsBinding
import kotlinx.android.synthetic.main.fragment_followed_artists.*
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
        viewModel.artists.observe(this, Observer {
            setupFollowedArtistsAdapter()
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
        add.setOnClickListener {
            navigateToFollowFragment()
        }
    }

    private fun navigateToFollowFragment() {
        val action = FollowedArtistsFragmentDirections.actionFollowedArtistsFragmentToFollowFragment(viewModel.followedArtists)
        findNavController().navigate(action)
    }
}
