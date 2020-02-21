package com.deniz.easify.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.easify.data.FakePlayerRepository
import com.deniz.easify.data.FakeTrackRepository
import com.deniz.easify.data.source.repositories.PlayerRepository
import com.deniz.easify.util.EventObserver
import com.deniz.easify.util.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @User: deniz.demirci
 * @Date: 2020-02-21
 */

@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var historyViewModel: HistoryViewModel

    // Use a fake repository to be injected into the viewModel
    private lateinit var playerRepository: FakePlayerRepository

    private lateinit var eventObserver: EventObserver<HistoryViewEvent>

    @Before
    fun setup() {
        eventObserver = mockk(relaxed = true)
        playerRepository = FakePlayerRepository()
        historyViewModel = HistoryViewModel(playerRepository)
    }

    /**
     * This test fails due to the fact that mocked HistoryObject doesn't have History object
     * and therefore the filtering operation inside viewModel's fetchRecentlyPlayedSongs()
     * method fails.
     */
    @Test
    fun `when fetchRecentlyPlayedSongs() returns success, NotifyDataChanged event is fired`() = mainCoroutineRule.runBlockingTest {
        historyViewModel.fetchRecentlyPlayedSongs()

        Truth.assertThat(historyViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(HistoryViewEvent.NotifyDataChanged::class.java)
    }

    @Test
    fun `when fetchRecentlyPlayedSongs() returns error, ShowError event is fired`() = mainCoroutineRule.runBlockingTest {
        playerRepository.shouldReturnError = true
        historyViewModel.fetchRecentlyPlayedSongs()

        Truth.assertThat(historyViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(HistoryViewEvent.ShowError::class.java)
    }

    @Test
    fun `when openTrack() is called, OpenFeaturesFragment event is fired`() = mainCoroutineRule.runBlockingTest {
        historyViewModel.openTrack(mockk())

        Truth.assertThat(historyViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(HistoryViewEvent.OpenFeaturesFragment::class.java)
    }

    @Test
    fun `when openPlaylistsPage() is called, OpenPlaylistsFragment event is fired`() = mainCoroutineRule.runBlockingTest {
        historyViewModel.openPlaylistsPage(mockk())

        Truth.assertThat(historyViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(HistoryViewEvent.OpenPlaylistsFragment::class.java)
    }

    @After
    fun tearDown() {
        historyViewModel.event.removeObserver(eventObserver)
    }
}
