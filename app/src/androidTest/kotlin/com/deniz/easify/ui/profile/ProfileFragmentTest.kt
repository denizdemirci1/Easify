package com.deniz.easify.ui.profile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deniz.easify.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * @User: deniz.demirci
 * @Date: 2020-02-21
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class ProfileFragmentTest {

    @Test
    fun whenClickedOnFavoritesLayout_openedFavoritesFragment() {

        // Create a mocked NavController
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<ProfileFragment>(null, R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - favorites layout is clicked
        Espresso.onView(ViewMatchers.withId(R.id.favorites)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the favorites screen
        Mockito.verify(navController).navigate(
            ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment()
        )
    }

    @Test
    fun whenClickedOnFollowedArtistsLayout_openedFollowedArtistsFragment() {

        // Create a mocked NavController
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<ProfileFragment>(null, R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - followed_artists layout is clicked
        Espresso.onView(ViewMatchers.withId(R.id.followed_artists)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the followed_artists screen
        Mockito.verify(navController).navigate(
            ProfileFragmentDirections.actionProfileFragmentToFollowedArtistsFragment()
        )
    }

    @Test
    fun whenClickedOnPlaylistsLayout_openedPlaylistsFragment() {

        // Create a mocked NavController
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<ProfileFragment>(null, R.style.AppTheme)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // WHEN - playlists layout is clicked
        Espresso.onView(ViewMatchers.withId(R.id.playlists)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the playlists screen
        Mockito.verify(navController).navigate(
            ProfileFragmentDirections.actionProfileFragmentToPlaylistFragment()
        )
    }

    @Test
    fun whenFragmentIsCreated_userInfoIsDisplayed() {

        launchFragmentInContainer<ProfileFragment>(null, R.style.AppTheme)

        // WHEN - playlists layout is clicked
        Espresso.onView(ViewMatchers.withId(R.id.profile_picture)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.username)).check(matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.follower_count)).check(matches(isDisplayed()))

    }
}