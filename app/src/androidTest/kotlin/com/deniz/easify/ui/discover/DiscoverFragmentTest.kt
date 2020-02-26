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
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.ui.search.features.discover.DiscoverFragment
import com.deniz.easify.ui.search.features.discover.DiscoverFragmentArgs
import com.deniz.easify.ui.search.features.discover.DiscoverFragmentDirections
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * @User: deniz.demirci
 * @Date: 2020-02-06
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class DiscoverFragmentTest {

    @Test
    fun whenClickedOnDiscoverButton_openedRecommendedTracksFragment() {

        // Create a mocked NavController
        val navController = mock(NavController::class.java)

        // GIVEN - On the discover screen
        val featuresObject = FeaturesObject(
            acousticness = 0.00671f,
            danceability = 0.6f,
            energy = 0.8f,
            id = "1zB4vmk8tFRmM9UULNzbLB",
            instrumentalness = 0.1f,
            liveness = 0.1f,
            speechiness = 0.05f,
            tempo = 167.9f,
            valence = 0.28f
        )

        val bundle = DiscoverFragmentArgs(featuresObject).toBundle()
        val scenario = launchFragmentInContainer<DiscoverFragment>(bundle, R.style.AppTheme)

        scenario.onFragment { fragment ->
            fragment.arguments = bundle
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - discover button is clicked
        onView(withId(R.id.discover)).perform(click())

        // THEN - Verify that we navigate to the recommended tracks screen
        verify(navController).navigate(
            DiscoverFragmentDirections.actionDiscoverFragmentToRecommendedTracksFragment()
        )
    }
}
