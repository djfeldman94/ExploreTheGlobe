package com.etg.core.domain.usecase

import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository

/**
 * Use case to get the list of countries
 */
class GetCountriesUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getCountries()
    }
}
