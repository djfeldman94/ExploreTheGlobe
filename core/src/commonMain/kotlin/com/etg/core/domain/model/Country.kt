package com.etg.core.domain.model

@kotlinx.serialization.Serializable
data class Country(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)
