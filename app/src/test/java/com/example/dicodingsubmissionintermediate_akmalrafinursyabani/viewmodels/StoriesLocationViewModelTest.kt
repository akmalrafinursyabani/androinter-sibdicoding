package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.DataStoriesLocationDummy
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.MainDispatcherRule
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.StoriesLocationRepository
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.getOrAwaitValue
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItemWithLocation
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoriesWithLocationResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse
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
class StoriesLocationViewModelTest {
    @get:Rule
    val InstantTask = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var storiesLocationRepository = mock(StoriesLocationRepository::class.java)

    private lateinit var storiesLocationViewModel: StoriesLocationViewModel
    private val successStoriesLocationResponse =
        DataStoriesLocationDummy.initDataStoriesLocationDummy()

    @Before
    fun setup() {
        storiesLocationViewModel = StoriesLocationViewModel(storiesLocationRepository)
    }

    @Test
    fun `when Stories Location is fetched and successful`() {
        val expectedResult = MutableLiveData<Result<StoriesWithLocationResponse>>()
        expectedResult.value =
            Result.Success(successStoriesLocationResponse)
        Mockito.`when`(
            storiesLocationRepository.storiesMarkerBounds()
        ).thenReturn(expectedResult)
        val actualResult =
            storiesLocationViewModel.storiesLocation()
                .getOrAwaitValue()
        Mockito.verify(storiesLocationRepository)
            .storiesMarkerBounds()
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Success)
    }

    @Test
    fun `when Stories Location is failed and should return error`() {
        val expectedResult = MutableLiveData<Result<StoriesWithLocationResponse>>()
        expectedResult.value =
            Result.Error("Stories failed to fetch")
        Mockito.`when`(
            storiesLocationRepository.storiesMarkerBounds()
        ).thenReturn(expectedResult)
        val actualResult =
            storiesLocationViewModel.storiesLocation()
                .getOrAwaitValue()
        Mockito.verify(storiesLocationRepository)
            .storiesMarkerBounds()
        assertNotNull(actualResult)
        assertTrue(actualResult is Result.Error)
    }

}