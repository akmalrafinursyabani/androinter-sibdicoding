package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataAddStoryDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataLoginDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.AddStoryRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.UploadStoryResponse
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
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val InstantTask = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var addStoryRepository = mock(AddStoryRepository::class.java)

    private lateinit var addStoryViewModel: AddStoryViewModel
    private val successAddStoryResponse = DataAddStoryDummy.initDataAddStoryDummy()

    private val dummyFile = File("mystory_dummy.jpg")
    private val dummyLat = "-0.9189189"
    private val dummyLon = "100.42733"


    @Before
    fun setup() {
        addStoryViewModel = AddStoryViewModel(addStoryRepository)
    }

    @Test
    fun `when Add Story is successful`() {
        val expectedResult = MutableLiveData<Result<UploadStoryResponse>>()
        expectedResult.value =
            Result.Success(successAddStoryResponse)
        Mockito.`when`(
            addStoryRepository.uploadStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
        ).thenReturn(expectedResult)
        val actualResult =
            addStoryViewModel.addStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
                .getOrAwaitValue()
        Mockito.verify(addStoryRepository)
            .uploadStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }
    @Test
    fun `when Add Story is failed`() {
        val expectedResult = MutableLiveData<Result<UploadStoryResponse>>()
        expectedResult.value =
            Result.Error("Gagal upload story")
        Mockito.`when`(
            addStoryRepository.uploadStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
        ).thenReturn(expectedResult)
        val actualResult =
            addStoryViewModel.addStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
                .getOrAwaitValue()
        Mockito.verify(addStoryRepository)
            .uploadStory(
                file = File("mystory_dummy.jpg"),
                lat = dummyLat,
                lng = dummyLon
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }
}