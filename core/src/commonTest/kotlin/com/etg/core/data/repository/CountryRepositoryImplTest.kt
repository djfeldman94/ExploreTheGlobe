package com.etg.core.data.repository

import com.etg.core.data.remote.CountryApi
import com.etg.core.util.TestData
import io.ktor.client.plugins.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryRepositoryImplTest {
    private val mockApi = mockk<CountryApi>()
    private val repository = CountryRepositoryImpl(mockApi)

    @Test
    fun testGetCountries_Success() = runTest {
        // Given
        coEvery { mockApi.getCountries() } returns TestData.testCountries

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TestData.testCountries, result.getOrNull())
    }

    @Test
    fun testGetCountries_ApiError() = runTest {
        // Given
        coEvery { mockApi.getCountries() } throws ClientRequestException(
            mockk(relaxed = true),
            "HTTP Error"
        )

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

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
