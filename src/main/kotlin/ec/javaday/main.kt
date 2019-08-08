package ec.javaday

import com.example.serializer.DateTimeAdapter
import com.example.serializer.UUIDAdapter
import com.squareup.moshi.Moshi
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

val databaseUser = "pepe"
val databasePassword = "12345678"
val databaseDriver = "org.postgresql.Driver"
val databaseUrl = "jdbc:postgresql://localhost/tweet-query-db"

val moshi: Moshi = Moshi
    .Builder()
    .add(DateTimeAdapter())
    .add(UUIDAdapter())
    .build()

fun main() {
    executeMigrations()
    Database.connect(databaseUrl, databaseDriver, databaseUser, databasePassword)
}

fun executeMigrations() =
    Flyway
        .configure()
        .dataSource(databaseUrl, databaseUser, databasePassword)
        .load()
        .migrate()
