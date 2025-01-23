package com.etg.core.data.repository

import com.etg.core.data.dao.CountryDao
import com.etg.core.data.remote.CountryApi
import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CountryRepositoryImpl(
    private val api: CountryApi,
    private val countryDao: CountryDao,
    scope: CoroutineScope,
) : CountryRepository {
    init {
        // Ensure that the database is initialized
        scope.launch {
            if (countryDao.getCountries().isEmpty()) {
                try {
                    refreshCountries()
                } catch (e: Exception) {
                    // Fail silently for now
                    e.printStackTrace()
                }
            }
        }

    }

    override suspend fun refreshCountries() {
        val remoteCountries = api.getCountries().getOrThrow()
        countryDao.insertCountries(remoteCountries)
    }

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val countries = countryDao.getCountries()
            if (countries.isEmpty()) {
                refreshCountries()
                val refreshedCountries = countryDao.getCountries()
                if (refreshedCountries.isEmpty()) {
                    Result.failure(Exception("Failed to fetch countries"))
                } else {
                    Result.success(refreshedCountries)
                }
            } else {
                Result.success(countries)
            }
        } catch (e: ClientRequestException) {
            Result.failure(Exception("Failed to fetch countries: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}