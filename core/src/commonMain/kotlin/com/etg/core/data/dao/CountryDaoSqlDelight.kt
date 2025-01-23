package com.etg.core.data.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.etg.core.domain.model.Country
import com.etg.db.Country_table
import com.etg.db.Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

/**
 * SQLDelight implementation of [CountryDao].
 */
class CountryDaoSqlDelight(
    private val database: Database,
    private val dispatcher: CoroutineContext,
) : CountryDao {
    override fun getCountriesFlow(): Flow<List<Country>> {
        return database.countryQueries.selectAllCountries().asFlow().mapToList(dispatcher).map {
            it.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getCountries(): List<Country> {
        return database.countryQueries.selectAllCountries().executeAsList().map {
            it.toDomain()
        }
    }

    override suspend fun deleteAllCountries() {
        database.countryQueries.deleteAllCountries()
    }

    override suspend fun insertCountries(countries: List<Country>) {
        database.countryQueries.transaction {
            countries.forEach { country ->
                database.countryQueries.insertCountry(
                    name = country.name,
                    region = country.region,
                    code = country.code,
                    capital = country.capital,
                )
            }
        }
    }
}

fun Country_table.toDomain(): Country {
    return Country(
        name = name,
        region = region,
        code = code,
        capital = capital,
    )
}

