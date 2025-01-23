package com.etg.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.etg.db.Database
import org.koin.dsl.module

actual fun databaseDriverModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = Database.Schema,
            name =  "etg.db"
        )
    }
}