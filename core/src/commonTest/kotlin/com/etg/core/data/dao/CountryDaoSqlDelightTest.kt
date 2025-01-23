package com.etg.core.data.dao

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.etg.core.domain.model.Country
import com.etg.db.Database
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryDaoSqlDelightTest {
    private lateinit var database: Database
    private lateinit var driver: SqlDriver
    private lateinit var countryDao: CountryDaoSqlDelight
    private val testDispatcher = StandardTestDispatcher()

    private val testCountries = listOf(
        Country(
            name = "Test Country 1",
            region = "Test Region 1",
            code = "TC1",
            capital = "Test Capital 1"
        ),
        Country(
            name = "Test Country 2",
            region = "Test Region 2",
            code = "TC2",
            capital = "Test Capital 2"
        )
    )

    @BeforeTest
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        database = Database(driver)
        countryDao = CountryDaoSqlDelight(database, testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun insertAndGetCountries() = runTest(testDispatcher) {
        // When inserting countries
        countryDao.insertCountries(testCountries)

        // Then getting countries returns the inserted data
        val result = countryDao.getCountries()
        assertEquals(testCountries.size, result.size)
        assertEquals(testCountries[0].name, result[0].name)
        assertEquals(testCountries[0].region, result[0].region)
        assertEquals(testCountries[0].code, result[0].code)
        assertEquals(testCountries[0].capital, result[0].capital)
    }

    @Test
    fun getCountriesFlow() = runTest(testDispatcher) {
        // When inserting countries
        countryDao.insertCountries(testCountries)

        // Then the flow emits the inserted data
        val result = countryDao.getCountriesFlow().first()
        assertEquals(testCountries.size, result.size)
        assertEquals(testCountries[0].name, result[0].name)
        assertEquals(testCountries[0].region, result[0].region)
    }

    @Test
    fun deleteAllCountries() = runTest(testDispatcher) {
        // Given some countries in the database
        countryDao.insertCountries(testCountries)
        
        // When deleting all countries
        countryDao.deleteAllCountries()
        
        // Then the database should be empty
        val result = countryDao.getCountries()
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertCountriesReplacesExistingData() = runTest(testDispatcher) {
        // Given some initial countries
        countryDao.insertCountries(testCountries)
        
        // When inserting new countries
        val newCountries = listOf(
            Country(
                name = "New Country",
                region = "New Region",
                code = "NC",
                capital = "New Capital"
            )
        )
        countryDao.insertCountries(newCountries)
        
        // Then only the new countries should exist
        val result = countryDao.getCountries()
        assertEquals(1, result.size)
        assertEquals("New Country", result[0].name)
    }
}
