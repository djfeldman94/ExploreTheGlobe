package com.etg.core.domain.model

/**
 * Data class representing a country
 *
 * With more time, this would be a separate model from the DTO model so that it is not as tightly coupled to the API response
 *
 * Also for now, we provide a blank default value for each field in case the API response is missing a field
 */
@kotlinx.serialization.Serializable
data class Country(
    val name: String = "",
    val region: String = "",
    val code: String = "",
    val capital: String = "",
)
