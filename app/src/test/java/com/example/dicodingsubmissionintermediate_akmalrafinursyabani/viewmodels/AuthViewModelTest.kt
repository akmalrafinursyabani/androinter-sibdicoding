package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataAuthDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.AuthRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {
    @get:Rule
    val InstantTask = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var authRepository = mock(AuthRepository::class.java)

    private lateinit var authViewModel: AuthViewModel
    private val successAuthResponse = DataAuthDummy.initAuthDummy()

    @Before
    fun setup() {
        authViewModel = AuthViewModel(authRepository)
    }

    @Test
    fun `when logout is successful`() {
        val expectedResult = MutableLiveData<Result<String>>()
        expectedResult.value =
            Result.Success(successAuthResponse)
        Mockito.`when`(
            authRepository.logout()
        ).thenReturn(expectedResult)
        val actualResult =
            authViewModel.logout()
                .getOrAwaitValue()
        Mockito.verify(authRepository).logout()
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }

    @Test
    fun `when logout is failed`() {
        val expectedResult = MutableLiveData<Result<String>>()
        expectedResult.value =
            Result.Error("Token is invalid or session expired!")
        Mockito.`when`(
            authRepository.logout()
        ).thenReturn(expectedResult)
        val actualResult =
            authViewModel.logout()
                .getOrAwaitValue()
        Mockito.verify(authRepository).logout()
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }
}