package com.etg.core.data.dao

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.etg.db.Database

private var index = 0
actual fun createTestSqlDriver(): SqlDriver {
    index++
    val schema = Database.Schema
    return NativeSqliteDriver(
        DatabaseConfiguration(
            name = "test-$index.db",
            version = schema.version.toInt(),
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) {
                    schema.migrate(it, oldVersion.toLong(), newVersion.toLong())
                }
            },
            inMemory = true
        )
    )
}