package com.deniz.easify.extension

import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

/**
 * Prevents clicking on selected bottom navigation bar item to re-create the fragment
 */
fun BottomNavigationView.disableCurrentFragmentRecreating() {
    this.setOnNavigationItemReselectedListener { }
}
