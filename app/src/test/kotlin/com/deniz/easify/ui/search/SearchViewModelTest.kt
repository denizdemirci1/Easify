package com.deniz.easify.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.easify.data.FakeTrackRepository
import com.deniz.easify.util.EventObserver
import com.deniz.easify.util.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
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
    private lateinit var trackRepository: FakeTrackRepository

    private lateinit var eventObserver: EventObserver<SearchViewEvent>

    @Before
    fun setup() {
        eventObserver = mockk(relaxed = true)
        trackRepository = FakeTrackRepository()
        searchViewModel = SearchViewModel(trackRepository)
    }

    /**
     * This next 2 tests fail due to the fact that mocked Track Object doesn't have
     * tracks, and therefore there is no track.name or artist.name which are needed
     * in the filtering operation inside viewModel's fetchSongs() method
     */
    @Test
    fun `when input length is greater than 2, there are tracks to show`() = mainCoroutineRule.runBlockingTest {
        searchViewModel.fetchSongs("brooklyn")
        Truth.assertThat(searchViewModel.tracksToShow).isNotEmpty()
    }

    @Test
    fun `when input length is equal to 2, there are tracks to show`() = mainCoroutineRule.runBlockingTest {
        searchViewModel.fetchSongs("br")
        Truth.assertThat(searchViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(SearchViewEvent.NotifyDataChanged::class.java)
    }

    @Test
    fun `when input length is smaller than 2, there are no tracks to show`() = mainCoroutineRule.runBlockingTest {
        searchViewModel.fetchSongs("b")
        Truth.assertThat(searchViewModel.tracksToShow).isEmpty()
    }

    @Test
    fun `when input length is zero, there are no tracks to show`() = mainCoroutineRule.runBlockingTest {
        searchViewModel.fetchSongs("")
        Truth.assertThat(searchViewModel.tracksToShow).isEmpty()
    }

    @Test
    fun `when search returns error, event is ShowError`() = mainCoroutineRule.runBlockingTest {
        trackRepository.shouldReturnError = true
        searchViewModel.fetchSongs("br")
        Truth.assertThat(searchViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(SearchViewEvent.ShowError::class.java)
    }

    @After
    fun tearDown() {
        searchViewModel.event.removeObserver(eventObserver)
    }
}
