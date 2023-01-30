package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataDetailStoryDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataStoryDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.DetailStoryRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailStoryViewModelTest {
    @get:Rule
    val InstantTask = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var detailStoryRepository = mock(DetailStoryRepository::class.java)

    private lateinit var detailStoryViewModel: DetailStoryViewModel
    private val successStoryDetailResponse = DataDetailStoryDummy.initDataDetailStoryDummy()

    @Before
    fun setup() {
        detailStoryViewModel = DetailStoryViewModel(detailStoryRepository)
    }

    @Test
    fun `when Detail story fetched and successful`() {
        val expectedResult = MutableLiveData<Result<StoryDetailResponse>>()
        expectedResult.value =
            Result.Success(successStoryDetailResponse)
        `when`(
            detailStoryRepository.getStoryDetail(
                idStory = "dummy_idstory",
            )
        ).thenReturn(expectedResult)
        val actualResult =
            detailStoryViewModel.getStoryDetail(
                id = "dummy_idstory",
            )
                .getOrAwaitValue()
        Mockito.verify(detailStoryRepository)
            .getStoryDetail(
                idStory = "dummy_idstory",
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }

    @Test
    fun `when Detail story failed`() {
        val expectedResult = MutableLiveData<Result<StoryDetailResponse>>()
        expectedResult.value =
            Result.Error("Something went wrong when fetching story.")
        `when`(
            detailStoryRepository.getStoryDetail(
                idStory = "dummy_idstory",
            )
        ).thenReturn(expectedResult)
        val actualResult =
            detailStoryViewModel.getStoryDetail(
                id = "dummy_idstory",
            )
                .getOrAwaitValue()
        Mockito.verify(detailStoryRepository)
            .getStoryDetail(
                idStory = "dummy_idstory",
            )
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }


}