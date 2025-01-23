package com.etg.core.di

import com.etg.core.data.dao.CountryDao
import com.etg.core.data.dao.CountryDaoSqlDelight
import com.etg.core.data.remote.CountryApi
import com.etg.core.data.remote.CountryApiImpl
import com.etg.core.data.repository.CountryRepositoryImpl
import com.etg.core.domain.repository.CountryRepository
import com.etg.core.domain.usecase.GetCountriesUseCase
import com.etg.db.Database
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun databaseDriverModule(): Module

/**
 * Core module for koin DI
 *
 * Includes:
 * - [HttpClient] for network requests
 * - [CountryApi] for fetching country data
 * - [CountryDao] for storing country data
 * - [Database] for initializing the database
 * - [CountryRepository] for fetching and storing country data
 * - [GetCountriesUseCase] for fetching country data
 * - [databaseDriverModule] for initializing the database driver
 */
val coreModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<CountryApi> {
        CountryApiImpl(get())
    }
    single<CountryDao> {
        CountryDaoSqlDelight(get(), get())
    }
    single<Database> {
        Database(get())
    }
    single<CountryRepository> {
        CountryRepositoryImpl(get(), get(), get())
    }
    single<GetCountriesUseCase> {
        GetCountriesUseCase(get())
    }
    includes(databaseDriverModule())
}