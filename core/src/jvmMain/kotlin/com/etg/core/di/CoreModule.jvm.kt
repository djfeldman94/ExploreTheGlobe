package com.etg.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.etg.db.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun databaseDriverModule() = module {
    single<SqlDriver> {
        JdbcSqliteDriver("jdbc:sqlite:etg.db")
    }
}