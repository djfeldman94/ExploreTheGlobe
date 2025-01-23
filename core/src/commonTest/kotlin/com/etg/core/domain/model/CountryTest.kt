package com.etg.core.domain.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class CountryTest {
    private val json = Json { 
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Test
    fun testCountrySerialization() {
        val country = Country(
            name = "United States",
            region = "Americas",
            code = "US",
            capital = "Washington, D.C."
        )

        val jsonString = json.encodeToString(country)
        val decoded = json.decodeFromString<Country>(jsonString)

        assertEquals(country, decoded)
    }

    @Test
    fun testCountryListDeserialization() {
        val jsonString = """
            [
                {
                    "name": "United States",
                    "region": "Americas",
                    "code": "US",
                    "capital": "Washington, D.C."
                }
            ]
        """.trimIndent()

        val countries = json.decodeFromString<List<Country>>(jsonString)
        assertEquals(1, countries.size)
        assertEquals("United States", countries[0].name)
        assertEquals("Americas", countries[0].region)
        assertEquals("US", countries[0].code)
        assertEquals("Washington, D.C.", countries[0].capital)
    }
}
