package com.deniz.easify.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.deniz.easify.R
import com.deniz.easify.databinding.ActivitySplashBinding
import com.deniz.easify.ui.main.MainActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel by viewModel<SplashViewModel>()

    companion object {
        private const val SPOTIFY_REQUEST_CODE = 1337
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.bind(root)
        binding.splashViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.authenticateSpotify()

        viewModel.authenticationRequest.observe(this, Observer {
            AuthenticationClient.openLoginActivity(
                this,
                SPOTIFY_REQUEST_CODE,
                viewModel.authenticationRequest.value)
        })

        viewModel.errorMessage.observe(this, Observer {
            showNetworkError(viewModel.errorMessage.value!!)
        })

        viewModel.navigateToMain.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        })
    }

    private fun showNetworkError(message: String) {
        this@SplashActivity.let {
            MaterialDialog(it).show {
                title(R.string.dialog_error_title)
                message(text = message)
                positiveButton(R.string.dialog_positive_button_text) {
                    viewModel.authenticateSpotify()
                }
                negativeButton(R.string.dialog_negative_button_text) {
                    finish()
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
