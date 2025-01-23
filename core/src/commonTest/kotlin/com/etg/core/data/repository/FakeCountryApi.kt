package com.etg.core.data.repository

import com.etg.core.data.remote.CountryApi
import com.etg.core.domain.model.Country

class FakeCountryApi : CountryApi {
    private var shouldThrowError = false
    private var error: Exception? = null
    private var countries = emptyList<Country>()

    fun setCountries(newCountries: List<Country>) {
        countries = newCountries
    }

    fun setShouldThrowError(shouldThrow: Boolean, exception: Exception? = null) {
        shouldThrowError = shouldThrow
        error = exception
    }

    override suspend fun getCountries(): Result<List<Country>> {
        if (shouldThrowError) {
            return Result.failure(error ?: Exception("Test exception"))
        }
        return Result.success(countries)
    }
}
