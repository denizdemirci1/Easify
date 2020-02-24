package com.deniz.easify.ui.history

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * @User: deniz.demirci
 * @Date: 2020-02-21
 */

/**
 * This test involves coroutine calls for network request.
 * If token is expired, request returns error 401 and material dialog shows up.
 * And the test fail. So get a fresh token before running this test.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class HistoryFragmentTest {

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun whenClickedOnRecyclerViewItem_openedFeaturesFragment() {

        // Create a mocked NavController
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<HistoryFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - an item of the recyclerView is clicked
        Espresso.onView(ViewMatchers.withId(R.id.tracks_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryViewHolder>(0, click()))

        // THEN - Verify that we navigate to the FeaturesFragment screen
        //TODO: fails because mocked Track is not the same with clicked Track.
        val track = Mockito.mock(Track::class.java)
        Mockito.verify(navController).navigate(
            HistoryFragmentDirections.actionHistoryFragmentToFeaturesFragment(track)
        )
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}