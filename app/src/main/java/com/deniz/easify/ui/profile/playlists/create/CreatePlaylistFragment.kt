package com.deniz.easify.ui.profile.playlists.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentCreatePlaylistBinding
import com.deniz.easify.extension.hideKeyboard
import com.deniz.easify.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-01-28
 */


class CreatePlaylistFragment : Fragment() {

    private lateinit var binding: FragmentCreatePlaylistBinding

    private val viewModel by viewModel<CreatePlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_create_playlist, container, false)
        binding = FragmentCreatePlaylistBinding.bind(root).apply {
            this.viewmodel = viewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.create.setOnClickListener {
            binding.create.isEnabled = false
            it.hideKeyboard()

            val name =
                if (binding.name.text.toString().isEmpty())
                    getString(R.string.fragment_create_playlist_default_playlist_name)
                else
                    binding.name.text.toString()

            val description =
                if (binding.description.text.toString().isEmpty())
                    getString(R.string.fragment_create_playlist_default_playlist_description)
                else
                    binding.description.text.toString()

            viewModel.createPlaylist(name, description)
        }
    }

    private fun setupObservers() {
        viewModel.navigate.observe(this, EventObserver{
            navigateBackToPlaylistsFragment()
        })
    }

    private fun navigateBackToPlaylistsFragment() {
        findNavController().popBackStack()
    }
}