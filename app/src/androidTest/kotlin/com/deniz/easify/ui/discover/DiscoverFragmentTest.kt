package com.deniz.easify.ui.discover

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import com.deniz.easify.data.FakeRepository
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.ui.search.features.discover.DiscoverFragment
import com.deniz.easify.ui.search.features.discover.DiscoverFragmentArgs
import com.deniz.easify.ui.search.features.discover.DiscoverFragmentDirections
import com.deniz.easify.ui.search.features.discover.DiscoverViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @User: deniz.demirci
 * @Date: 2020-02-06
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class DiscoverFragmentTest {

    @Test
    fun whenClickedOnDiscoverButton_openedRecommendedTracksFragment() = runBlockingTest {
        // GIVEN - On the landing screen
        val featuresObject = FeaturesObject(
                        acousticness = 0.00671f,
                        danceability = 0.6f,
                        duration_ms = 187147,
                        energy = 0.8f,
                        id = "1zB4vmk8tFRmM9UULNzbLB",
                        instrumentalness = 0.1f,
                        key = 0,
                        liveness = 0.1f,
                        loudness = -4.8f,
                        mode = 1,
                        speechiness = 0.05f,
                        time_signature = 4,
                        tempo = 167.9f,
                        type = "audio_features",
                        uri = "salşda",
                        valence = 0.28f
                    )
        val bundle = DiscoverFragmentArgs(featuresObject).toBundle()
        val scenario = launchFragmentInContainer<DiscoverFragment>(bundle, R.style.AppTheme)

        val navController: NavController = mockk()
        scenario.onFragment { fragment ->
            fragment.arguments = bundle
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - login button is clicked
        onView(withId(R.id.discover)).perform(click())

        // THEN - Verify that we navigate to the login screen
        verify { navController.navigate(
            DiscoverFragmentDirections.actionDiscoverFragmentToRecommendedTracksFragment()
        ) }
    }
}