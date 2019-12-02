package com.deniz.easify.ui.main

import android.os.Bundle
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

        // For Toolbar if we add later
        // setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp()


}
