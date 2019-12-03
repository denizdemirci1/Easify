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
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.databinding.FragmentFavoritesBinding
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoritesFragment : Fragment() {

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

        viewModel.top.observe(this, Observer {
            openFavoriteDetailsFragment(it)
        })
    }

    private fun openFavoriteDetailsFragment(favorites: Track?) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteDetailsFragment(favorites, type.text.toString())
        findNavController().navigate(action)
    }

    private fun setupListeners() {
        show.setOnClickListener {
            if (limit.text.toString() != "0")
                viewModel.fetchTop(
                    type.text.toString(),
                    timeRange.text.toString(),
                    Integer.parseInt(limit.text.toString()))
        }

        typeLayout.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show{
                    listItems(items = listOf("artists", "tracks")) { _, _, text ->
                        type.setText(text)
                    }
                }
            }
        }

        timeRangeLayout.setOnClickListener {
            it.context.let { context ->
                MaterialDialog(context).show{
                    listItems(items = listOf("long_term", "medium_term", "short_term")) { _, _, text ->
                        type.setText(text)
                    }
                }
            }
        }
    }


}