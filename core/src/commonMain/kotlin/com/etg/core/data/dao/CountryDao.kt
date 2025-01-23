package com.etg.core.data.dao

import com.etg.core.domain.model.Country
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for country data.
 */
interface CountryDao {
    fun getCountriesFlow(): Flow<List<Country>>
    suspend fun getCountries(): List<Country>
    suspend fun insertCountries(countries: List<Country>)
    suspend fun deleteAllCountries()
}

