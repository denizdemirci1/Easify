package com.deniz.easify.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.BuildConfig
import com.deniz.easify.R
import com.deniz.easify.databinding.FragmentSplashBinding
import com.deniz.easify.util.EventObserver
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-02-06
 */


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModel<SplashViewModel>()

    companion object {
        private const val SPOTIFY_REQUEST_CODE = 1337
        private const val SCOPES = "user-read-recently-played," +
                "user-library-modify," +
                "user-read-email," +
                "user-read-private," +
                "user-top-read," +
                "user-follow-read," +
                "user-follow-modify," +
                "user-modify-playback-state," +
                "playlist-read-private," +
                "playlist-read-collaborative," +
                "playlist-modify-public," +
                "playlist-modify-private"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        binding = FragmentSplashBinding.bind(root).apply {
            this.viewModel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authenticateSpotify()
        setupObservers()
    }


    private fun setupObservers() {
        viewModel.authenticate.observe(this) {
            openSpotifyLoginActivity()
        }

        viewModel.errorMessage.observe(this) {
            showNetworkError(it)
        }

        viewModel.navigateToMain.observe(this, EventObserver {
            openSearchFragment()
        })
    }

    private fun openSpotifyLoginActivity() {
        val builder = AuthenticationRequest.Builder(
            BuildConfig.SPOTIFY_CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            getString(R.string.spotify_uri_callback)
        )

        builder.setScopes(arrayOf(SCOPES))

        AuthenticationClient.openLoginActivity(
            requireActivity(),
            SPOTIFY_REQUEST_CODE,
            builder.build())
    }

    private fun openSearchFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun showNetworkError(message: String) {
        requireActivity().let {
            MaterialDialog(it).show {
                title(R.string.dialog_error_title)
                message(text = message)
                positiveButton(R.string.dialog_positive_button_text) {
                    viewModel.authenticateSpotify()
                }
                negativeButton(R.string.dialog_negative_button_text) { _ ->
                    it.finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if result comes from the correct activity
        if (requestCode == SPOTIFY_REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, data)

            when (response.type) {
                // Response was successful and contains auth token
                AuthenticationResponse.Type.TOKEN -> {
                    viewModel.saveToken(response.accessToken)
                    viewModel.fetchUser()
                }

                // Auth flow returned an error
                AuthenticationResponse.Type.ERROR -> {
                    viewModel.clearToken()
                    viewModel.handleAuthError(response.error)
                }

                else -> showNetworkError("Something went wrong be canım.")
            }
        }
    }
}