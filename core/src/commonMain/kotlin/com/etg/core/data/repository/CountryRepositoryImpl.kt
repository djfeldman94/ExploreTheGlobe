package com.etg.core.data.repository

import com.etg.core.data.remote.CountryApi
import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository
import io.ktor.client.plugins.*

class CountryRepositoryImpl(
    private val api: CountryApi
) : CountryRepository {
    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            Result.success(api.getCountries())
        } catch (e: ClientRequestException) {
            Result.failure(Exception("Failed to fetch countries: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
