package com.etg.core.data.repository

import com.etg.core.util.TestData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryRepositoryImplTest {
    private val fakeApi = FakeCountryApi()
    private val fakeDao = FakeCountryDao()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val repository = CountryRepositoryImpl(fakeApi, fakeDao, CoroutineScope(
        UnconfinedTestDispatcher()
    ))

    @Test
    fun testGetCountries_Success() = runTest {
        // Given
        fakeApi.setCountries(TestData.testCountries)
        fakeDao.insertCountries(TestData.testCountries)

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TestData.testCountries, result.getOrNull())
    }

    @Test
    fun testGetCountries_ApiError() = runTest {
        // Given
        val exception = Exception("HTTP Error")
        fakeApi.setShouldThrowError(true, exception)
        fakeDao.setShouldThrowError(true, exception)

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("HTTP Error") == true)
    }

    @Test
    fun testGetCountries_GeneralError() = runTest {
        // Given
        val exception = IllegalStateException("Some error")
        fakeApi.setShouldThrowError(true, exception)
        fakeDao.setShouldThrowError(true, exception)

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
