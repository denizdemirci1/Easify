package com.deniz.easify.ui.features

import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import com.deniz.easify.data.source.remote.response.Album
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.ui.search.features.FeaturesFragment
import com.deniz.easify.ui.search.features.FeaturesFragmentArgs
import com.deniz.easify.util.EspressoIdlingResource
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.startsWith

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class FeaturesFragmentTest {

    private lateinit var navController: TestNavHostController

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Before
    fun setUp(){
        // Create a TestNavController
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
        navController.setCurrentDestination(R.id.featuresFragment)

        val track = Track(
            album = mock(Album::class.java),
            artists = arrayListOf(mock(Artist::class.java)),
            duration = 0,
            id = "06AKEBrKUckW0KREUWRnvT",
            name = "",
            popularity = 0,
            uri = ""
        )

        val bundle = FeaturesFragmentArgs(track).toBundle()
        val scenario = launchFragmentInContainer<FeaturesFragment>(bundle, R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun whenSearchButtonClicked_openedDiscoverFragment() {
        // WHEN - search button is clicked
        onView(withId(R.id.search)).perform(click())

        // THEN - Verify that we navigate to the DiscoverFragment screen
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.discoverFragment)
    }


    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}
