package ec.javaday

import com.squareup.moshi.Moshi
import ec.javaday.serializer.DateTimeAdapter
import ec.javaday.serializer.UUIDAdapter
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

val databaseUser = "javaday-api"
val databasePassword = "12345678"
val databaseUrl = "jdbc:postgresql://localhost/javaday-db"
val databaseDriver = "org.postgresql.Driver"


val moshiWithAdapters: Moshi = Moshi
    .Builder()
    .add(DateTimeAdapter())
    .add(UUIDAdapter())
    .build()


fun main(args: Array<String>) {
    executeMigrations()
    Database.connect(databaseUrl, databaseDriver, databaseUser, databasePassword)
    io.ktor.server.netty.EngineMain.main(args)
}


fun executeMigrations() =
    Flyway
        .configure()
        .dataSource(databaseUrl, databaseUser, databasePassword)
        .load()
        .migrate()
