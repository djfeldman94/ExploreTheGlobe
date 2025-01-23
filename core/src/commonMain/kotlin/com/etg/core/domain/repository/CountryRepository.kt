package com.etg.core.domain.repository

import com.etg.core.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
}
