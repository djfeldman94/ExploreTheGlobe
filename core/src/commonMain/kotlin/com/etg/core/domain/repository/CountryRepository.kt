package com.etg.core.domain.repository

import com.etg.core.domain.model.Country
import kotlinx.coroutines.flow.Flow

/**
 * Repository for country data
 */
interface CountryRepository {

    /**
     * Refresh the list of countries from the remote data source
     */
    suspend fun refreshCountries()

    /**
     * Get all the stored countries. This is offline first, so it will return the cached data first
     */
    suspend fun getCountries(): Result<List<Country>>
}
