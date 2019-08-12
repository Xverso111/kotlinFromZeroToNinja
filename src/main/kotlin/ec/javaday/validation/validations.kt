package ec.javaday.validation

import ec.javaday.exception.BusinessRuleException
import java.util.*

fun <T> List<T>.notEmpty(message: String) =
    this.takeIf { it.isNotEmpty() } ?: throw BusinessRuleException(message)

fun String.notBlank(message: String) =
    this.takeIf { it.isNotBlank() } ?: throw BusinessRuleException(message)

fun String?.validUUID(message: String): UUID =
    try {
        UUID.fromString(this)
    } catch (exception: Exception) {
        throw BusinessRuleException(message)
    }
