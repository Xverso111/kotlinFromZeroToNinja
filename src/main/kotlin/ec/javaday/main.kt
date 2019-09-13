package ec.javaday

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.moshi.Moshi
import ec.javaday.serializer.DateTimeAdapter
import ec.javaday.serializer.UUIDAdapter
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import java.util.*

val databaseUser = "javaday-api"
val databasePassword = "12345678"
val databaseUrl = "jdbc:postgresql://localhost/javaday-db"
val databaseDriver = "org.postgresql.Driver"


val moshiWithAdapters: Moshi = Moshi
    .Builder()
    .add(DateTimeAdapter())
    .add(UUIDAdapter())
    .build()

val objectMapper = jacksonObjectMapper().apply {
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    setTimeZone(TimeZone.getTimeZone("America/Guayaquil"))
    registerModule(JodaModule())
}

fun main(args: Array<String>) {
//    executeMigrations()
//    Database.connect(databaseUrl, databaseDriver, databaseUser, databasePassword)
    io.ktor.server.netty.EngineMain.main(args)
}


fun executeMigrations() =
    Flyway
        .configure()
        .dataSource(databaseUrl, databaseUser, databasePassword)
        .load()
        .migrate()
