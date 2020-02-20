package com.deniz.easify.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.easify.data.FakeTrackRepository
import com.deniz.easify.ui.search.SearchViewModel
import com.deniz.easify.util.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @User: deniz.demirci
 * @Date: 2020-01-10
 */

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var searchViewModel: SearchViewModel

    // Use a fake repository to be injected into the viewModel
    private lateinit var trackRepository : FakeTrackRepository

    @Before
    fun setup() {
        trackRepository = FakeTrackRepository()
        searchViewModel = SearchViewModel(trackRepository)
    }

    @Test
    fun `when input length is greater than 2, there are tracks to show`() = mainCoroutineRule.runBlockingTest {
        // Given an initialized searchViewModel

        // When fetchSongs is called with input size > 2
        searchViewModel.fetchSongs("brooklyn")

        // Then the [trackToShow] is populated
        Truth.assertThat(searchViewModel.tracksToShow).isNotEmpty()
    }

    @Test
    fun `when input length is equal to 2, there are tracks to show`() = mainCoroutineRule.runBlockingTest {
        // Given an initialized searchViewModel

        // When fetchSongs is called with input size = 2
        searchViewModel.fetchSongs("br")

        // Then the [trackToShow] is populated
        Truth.assertThat(searchViewModel.tracksToShow).isNotEmpty()
    }

    @Test
    fun `when input length is smaller than 2, there are no tracks to show`() = mainCoroutineRule.runBlockingTest {
        // Given an initialized searchViewModel

        // When fetchSongs is called with input size < 2
        searchViewModel.fetchSongs("b")

        // Then the [trackToShow] is populated
        Truth.assertThat(searchViewModel.tracksToShow).isEmpty()
    }

    @Test
    fun `when input length is zero, there are no tracks to show`() = mainCoroutineRule.runBlockingTest {
        // Given an initialized searchViewModel

        // When fetchSongs is called with input size < 2
        searchViewModel.fetchSongs("")

        // Then the [trackToShow] is populated
        Truth.assertThat(searchViewModel.tracksToShow).isEmpty()
    }
}
