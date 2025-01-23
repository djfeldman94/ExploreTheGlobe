package com.etg.core.di

import com.etg.core.data.dao.CountryDao
import com.etg.core.data.dao.CountryDaoSqlDelight
import com.etg.core.data.remote.CountryApi
import com.etg.core.data.repository.CountryRepositoryImpl
import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository
import com.etg.core.domain.usecase.GetCountriesUseCase
import com.etg.db.Database
import io.ktor.client.*
import io.ktor.client.plugins.HttpPlainText
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.nio.charset.Charset

expect fun databaseDriverModule(): Module

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
        CountryApi(get())
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