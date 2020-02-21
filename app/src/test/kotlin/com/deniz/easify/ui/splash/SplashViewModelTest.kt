package com.deniz.easify.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.easify.data.FakeUserRepository
import com.deniz.easify.util.EventObserver
import com.deniz.easify.util.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var splashViewModel: SplashViewModel

    // Use a fake repository to be injected into the viewModel
    private lateinit var userRepository: FakeUserRepository

    private lateinit var eventObserver: EventObserver<SplashViewEvent>

    @Before
    fun setup() {
        eventObserver = mockk(relaxed = true)
        userRepository = FakeUserRepository()
        splashViewModel = SplashViewModel(userRepository)
    }

    @Test
    fun `when fetchUser() returns Success, OpenSearchFragment event is fired`() = mainCoroutineRule.runBlockingTest {
        splashViewModel.fetchUser()

        Truth.assertThat(splashViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(SplashViewEvent.OpenSearchFragment::class.java)
    }

    @Test
    fun `when fetchUser() returns Error, if token is refreshed then ShowError event is fired`() = mainCoroutineRule.runBlockingTest {
        userRepository.shouldReturnError = true
        userRepository.fakeTokenRefreshed = true
        splashViewModel.fetchUser()

        Truth.assertThat(splashViewModel.event.value?.getContentIfNotHandled())
            .isInstanceOf(SplashViewEvent.ShowError::class.java)
    }

    @After
    fun tearDown() {
        splashViewModel.event.removeObserver(eventObserver)
    }
}