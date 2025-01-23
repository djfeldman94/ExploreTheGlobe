package com.etg.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.etg.db.Database
import org.koin.dsl.module

actual fun databaseDriverModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = get(),
            name = "etg.db")
    }
}