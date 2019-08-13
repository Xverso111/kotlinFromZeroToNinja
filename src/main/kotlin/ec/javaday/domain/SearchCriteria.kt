package ec.javaday.domain

import org.joda.time.LocalDateTime
import org.joda.time.Interval

/***
 * Search Criteria
 * En su definicion crea un constructor primario
 * y a su vez define los atributos de la clase
 */
class SearchCriteria(
    name: String,
    val hashtags: List<String>,
    val dateRange: DateRange? = null,
    val allowRetweets: Boolean = false
) {

    val name = name
        .notBlank("Cannot create a SearchCriteria without a name")
}

//extension function
//agregar funcionalidad a la clase String
fun String.notBlank(message: String) = this.ifBlank { throw Exception(message) }

/**
 * Date Range
 * Clase de dominio para el manejo de rangos de fechas
 */
class DateRange(
    val start: LocalDateTime,
    val end: LocalDateTime
) {
    fun contains(date: LocalDateTime) = Interval(
        start.toMillis(),
        end.toMillis()
    ).contains(date.toMillis())

    private fun LocalDateTime.toMillis() = this.toDateTime().toInstant().millis
}
