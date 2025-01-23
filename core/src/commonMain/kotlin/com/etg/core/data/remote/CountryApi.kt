package com.etg.core.data.remote

import com.etg.core.domain.model.Country
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class CountryApi(
    private val client: HttpClient
) {
    private val baseUrl = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

    private val deserializer = Json {
        ignoreUnknownKeys = true
    }
    suspend fun getCountries(): Result<List<Country>> {
        return try {
            Result.success(client.get(baseUrl).bodyAsText().let { jsonString ->
                deserializer.decodeFromString<List<Country>>(jsonString)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
