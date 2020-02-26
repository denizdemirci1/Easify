package com.deniz.easify.ui.history

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import com.deniz.easify.util.EspressoIdlingResource
import com.google.common.truth.Truth.assertThat
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
    fun whenClickedOnRecyclerViewFirstItem_openedFeaturesFragment() {

        // Create a TestNavController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        // Set current destination to the navController
        navController.setCurrentDestination(R.id.historyFragment)

        val scenario = launchFragmentInContainer<HistoryFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - first item of the recyclerView is clicked
        onView(withId(R.id.tracks_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryViewHolder>(0, click()))

        // THEN - Verify that we navigate to the FeaturesFragment screen
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.featuresFragment)
    }

    @Test
    fun whenClickedOnRecyclerViewLastItem_openedFeaturesFragment() {

        // Create a TestNavController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        // Set current destination to the navController
        navController.setCurrentDestination(R.id.historyFragment)

        var recyclerView = Mockito.mock(RecyclerView::class.java)
        val scenario = launchFragmentInContainer<HistoryFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            recyclerView = fragment.requireActivity().findViewById(R.id.tracks_recycler_view)
        }

        // Scroll to the bottom of [tracks_recycler_view]
        for (x in 1..5) {
            onView(withId(R.id.tracks_recycler_view)).perform(swipeUp())
        }

        // Find total item count in the recycler view
        val itemCount = recyclerView.adapter!!.itemCount

        // WHEN - last item of the recyclerView is clicked
        onView(withId(R.id.tracks_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryViewHolder>(itemCount - 1, click()))

        // THEN - Verify that we navigate to the FeaturesFragment screen
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.featuresFragment)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}
