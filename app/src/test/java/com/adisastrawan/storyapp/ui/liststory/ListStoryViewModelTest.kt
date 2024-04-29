package com.adisastrawan.storyapp.ui.liststory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.adisastrawan.storyapp.QuotePagingSource
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.repository.StoryAppRepository
import com.adisastrawan.storyapp.utils.DataDummy
import com.adisastrawan.storyapp.utils.MainDispatcherRule
import com.adisastrawan.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ListStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyAppRepository:StoryAppRepository

    @Test
    fun `when Get Story Should Not Null and Return Data`()= runTest{
        val dummyStories = DataDummy.generateDummyStories()
        val data = QuotePagingSource.snapshot(dummyStories)
        val expectedStories = MutableLiveData<PagingData<StoryEntity>>()
        expectedStories.value = data
        `when`(storyAppRepository.getStories()).thenReturn(expectedStories)

        val listStoryViewModel = ListStoryViewModel(storyAppRepository)
        val actualStories = listStoryViewModel.getStories().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStories.size,differ.snapshot().size)
        assertEquals(dummyStories[0],differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest{
        val data : PagingData<StoryEntity> = PagingData.from(emptyList())
        val expectedStories = MutableLiveData<PagingData<StoryEntity>>()
        expectedStories.value = data
        `when`(storyAppRepository.getStories()).thenReturn(expectedStories)
        val listStoryViewModel = ListStoryViewModel(storyAppRepository)
        val actualStories = listStoryViewModel.getStories().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        assertEquals(0,differ.snapshot().size)

    }
}
val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}