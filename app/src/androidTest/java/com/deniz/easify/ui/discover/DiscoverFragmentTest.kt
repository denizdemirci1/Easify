package com.deniz.easify.ui.discover

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import com.deniz.easify.ui.search.features.discover.DiscoverFragment
import com.deniz.easify.ui.search.features.discover.DiscoverFragmentDirections
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @User: deniz.demirci
 * @Date: 2020-02-06
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class DiscoverFragmentTest {

    @Test
    fun whenClickedOnLogin_openedLoginFragment() {
        // GIVEN - On the landing screen
        val scenario = launchFragmentInContainer<DiscoverFragment>(null, R.style.AppTheme)

        val navController: NavController = mockk()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - login button is clicked
        onView(withId(R.id.discover)).perform(click())

        // THEN - Verify that we navigate to the login screen
        verify { navController.navigate(
            DiscoverFragmentDirections.actionDiscoverFragmentToRecommendedTracksFragment(mockk())
        ) }
    }
}