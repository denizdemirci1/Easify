package com.deniz.easify.ui.profile.followings.follow

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
        setupFollowAdapter()
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.clear.setOnClickListener {
            binding.search.setText("")
            binding.search.hint = ""
        }
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is FollowViewEvent.NotifyDataChanged -> onViewDataChange(event.artists)

                is FollowViewEvent.ShowError -> showError(event.message)

                is FollowViewEvent.OpenArtistFragment -> navigateToArtistFragment(event.artist)
            }
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

    private fun onViewDataChange(artists: ArrayList<Artist>) {
        followAdapter.submitList(artists)
    }

    private fun showError(message: String) {
        MaterialDialog(requireContext()).show {
            title(R.string.dialog_error_title)
            message(text = message)
            positiveButton(R.string.dialog_ok)
        }
    }

    private fun navigateToArtistFragment(artist: Artist) {
        val action = FollowFragmentDirections.actionFollowFragmentToArtistFragment(artist)
        findNavController().navigate(action)
    }
}
