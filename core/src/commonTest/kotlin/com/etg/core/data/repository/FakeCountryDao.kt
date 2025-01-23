package com.etg.core.data.repository

import com.etg.core.data.dao.CountryDao
import com.etg.core.domain.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeCountryDao : CountryDao {
    private val countriesFlow = MutableStateFlow<List<Country>>(emptyList())
    private var shouldThrowError = false
    private var error: Exception? = null

    fun setShouldThrowError(throw: Boolean, exception: Exception? = null) {
        shouldThrowError = throw
        error = exception
    }

    override fun getCountriesFlow(): Flow<List<Country>> = countriesFlow.asStateFlow()

    override suspend fun getCountries(): List<Country> {
        if (shouldThrowError) {
            throw error ?: Exception("Test exception")
        }
        return countriesFlow.value
    }

    override suspend fun insertCountries(countries: List<Country>) {
        if (shouldThrowError) {
            throw error ?: Exception("Test exception")
        }
        countriesFlow.value = countries
    }

    override suspend fun deleteAllCountries() {
        if (shouldThrowError) {
            throw error ?: Exception("Test exception")
        }
        countriesFlow.value = emptyList()
    }
}
