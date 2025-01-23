package com.etg.core.data.repository

import com.etg.core.data.dao.CountryDao
import com.etg.core.data.remote.CountryApi
import com.etg.core.util.TestData
import io.ktor.client.plugins.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryRepositoryImplTest {
    private val mockApi = mockk<CountryApi>()
    private val mockDao = mockk<CountryDao>()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val repository = CountryRepositoryImpl(mockApi, mockDao, CoroutineScope(
        UnconfinedTestDispatcher()
    ))

    @Test
    fun testGetCountries_Success() = runTest {
        // Given
        coEvery { mockApi.getCountries() } returns Result.success(TestData.testCountries)
        coEvery { mockDao.getCountries() } returns TestData.testCountries


        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TestData.testCountries, result.getOrNull())
    }

    @Test
    fun testGetCountries_ApiError() = runTest {
        // Given
        val exception = ClientRequestException(
            mockk(relaxed = true),
            "HTTP Error"
        )
        coEvery { mockApi.getCountries() } throws exception
        coEvery { mockDao.getCountries() } throws exception

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Failed to fetch countries") == true)
    }

    @Test
    fun testGetCountries_GeneralError() = runTest {
        // Given
        val exception = IllegalStateException("Some error")
        coEvery { mockApi.getCountries() } throws exception
        coEvery { mockDao.getCountries() } throws exception

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
