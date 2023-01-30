package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataRegisterDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.RegisterRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
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
class RegisterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var registerRepository = mock(RegisterRepository::class.java)

    private lateinit var registerViewModel: RegisterViewModel
    private val successRegisterResponse = DataRegisterDummy.initDataRegisterDummy()

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(registerRepository)
    }

    @Test
    fun `when Register is successful`() {
        val expectedResult = MutableLiveData<Result<RegisterResponse>>()
        expectedResult.value =
            Result.Success(successRegisterResponse)
        Mockito.`when`(
            registerRepository.register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
        ).thenReturn(expectedResult)
        val actualResult =
            registerViewModel.register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
                .getOrAwaitValue()
        Mockito.verify(registerRepository)
            .register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }

    @Test
    fun `when Register is failed`() {
        val expectedResult = MutableLiveData<Result<RegisterResponse>>()
        expectedResult.value =
            Result.Error("Error! Email atau password salah!")
        Mockito.`when`(
            registerRepository.register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
        ).thenReturn(expectedResult)
        val actualResult =
            registerViewModel.register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
                .getOrAwaitValue()
        Mockito.verify(registerRepository)
            .register(
                name = "akmal123123",
                email = "akmalrafi.yourdev@gmail.com",
                password = "akmalkudubelajarlagi"
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }
}