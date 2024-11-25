package com.it.earthquake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.it.earthquake.model.Feature
import com.it.earthquake.model.Geometry
import com.it.earthquake.model.Properties
import com.it.earthquake.service.APIClient
import com.it.earthquake.viewmodel.EarthquakeListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EarthquakeListVIewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // 确保 LiveData 同步执行

    private val mockAPIClient = mockk<APIClient>() // 模拟 API 服务
    private val viewModel = EarthquakeListViewModel()

//    @Test
//    fun `fetchData should update LiveData with earthquake list`() = runBlockingTest {
        // 模拟返回数据
//        val mockFeatures = arrayListOf(
//            Feature(properties = Properties(mag = 7.2f, place = "Location A", time = 123456789L), id = "1", geometry = ),
//            Feature(properties = Properties(mag = 6.5f, place = "Location B", 987654321L), id = "2")
//        )
//        coEvery { mockAPIClient.APIService.getEarthquakes() } returns mockFeatures

        // 调用 ViewModel 方法
//        viewModel.fetchData()

        // 验证 LiveData 更新
//        assertEquals(mockFeatures, viewModel.mEarthquakeFeature.value)
//        assertEquals(false, viewModel.mErrorMessage.value)
//    }

//    @Test
//    fun `fetchData should handle API error`() = runBlockingTest {
//        // 模拟 API 异常
//        coEvery { mockAPIClient.APIService.getEarthquakes() } throws Exception("Network Error")
//
//        // 调用 fetchData
//        viewModel.fetchData()
//
//        // 验证错误处理逻辑
//        assertEquals(true, viewModel.mErrorMessage.value)
//        assertEquals(false, viewModel.mProgressStatus.value)
//    }
}