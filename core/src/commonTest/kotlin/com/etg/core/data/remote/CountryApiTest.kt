package com.etg.core.data.remote

import com.etg.core.util.TestData
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CountryApiTest {
    private fun createMockHttpClient(responseBody: String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
        return HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            engine {
                addHandler { request ->
                    respond(
                        content = responseBody,
                        status = status,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }
    }

    @Test
    fun testGetCountries_Success() = runTest {
        // Given
        val mockClient = createMockHttpClient(TestData.mockJsonResponse)
        val api = CountryApi(mockClient)

        // When
        val countries = api.getCountries()

        // Then
        assertEquals(249, countries.size)
        // TODO assertEquals(TestData.testCountries, countries)
    }

    @Test
    fun testGetCountries_HttpError() = runTest {
        // Given
        val mockClient = createMockHttpClient(
            responseBody = "Error",
            status = HttpStatusCode.InternalServerError
        )
        val api = CountryApi(mockClient)

        // Then
        assertFailsWith<Exception> {
            api.getCountries()
        }
    }
}
