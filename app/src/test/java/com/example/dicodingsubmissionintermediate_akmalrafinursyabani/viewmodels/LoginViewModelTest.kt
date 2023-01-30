package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataLoginDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.LoginRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val InstantTask = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var loginRepository = mock(LoginRepository::class.java)

    private lateinit var loginViewModel: LoginViewModel
    private val userData = User(
        id = "xzo122e89s",
        name = "Akmal Rafi",
        email = "akmalrafi.yourdev@gmail.com",
        token = "dummytokenasdij14892"
    )
    private val successLoginResponse = DataLoginDummy.initDataLoginDummy()
    private val email = "akmalrafi.yourdev@gmail.com"
    private val password = "akmal123123"

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(loginRepository)
    }

    @Test
    fun `when Login is successful`() {
        val expectedResult = MutableLiveData<Result<LoginResponse>>()
        expectedResult.value =
            Result.Success(successLoginResponse)
        `when`(
            loginRepository.login(
                email = "akmalrafi.yourdev@gmail.com",
                "akmal123123"
            )
        ).thenReturn(expectedResult)
        val actualResult =
            loginViewModel.login(email = "akmalrafi.yourdev@gmail.com", "akmal123123")
                .getOrAwaitValue()
        Mockito.verify(loginRepository).login(email = "akmalrafi.yourdev@gmail.com", "akmal123123")
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }

    @Test
    fun `when Login is failed`() {
        val expectedResult = MutableLiveData<Result<LoginResponse>>()
        expectedResult.value =
            Result.Error("Error! Email atau password salah!")
        `when`(
            loginRepository.login(
                email = "akmalrafi.yourdev@gmail.com",
                "akmal123123"
            )
        ).thenReturn(expectedResult)
        val actualResult =
            loginViewModel.login(email = "akmalrafi.yourdev@gmail.com", "akmal123123")
                .getOrAwaitValue()
        Mockito.verify(loginRepository).login(email = "akmalrafi.yourdev@gmail.com", "akmal123123")
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }

}