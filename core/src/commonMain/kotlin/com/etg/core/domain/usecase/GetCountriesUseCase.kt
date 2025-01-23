package com.etg.core.domain.usecase

import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository

class GetCountriesUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getCountries()
    }
}
