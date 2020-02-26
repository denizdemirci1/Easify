package com.deniz.easify.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.deniz.easify.R
import com.deniz.easify.extension.disableCurrentFragmentRecreating
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        // makes navigation do nothing when the fragment of clicked item is already on screen
        bottomNavigationView.disableCurrentFragmentRecreating()

        // shows bottom navigation bar only on top 3 fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment,
                R.id.historyFragment,
                R.id.profileFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }
        }

        // For Toolbar if we add later
        // setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp()

    /**
     * To be able to call onActivityResult from SplashFragment for Spotify Callback
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val splashFragment = supportFragmentManager.fragments[0].childFragmentManager.fragments[0]
        splashFragment?.onActivityResult(requestCode, resultCode, data)
    }
}
