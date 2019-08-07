package com.javaday.domain

class TweetQuery(
    name: String,
    val hashTags: List<String>,
    val dateRange: DateRange? = null
) {

    val name = name.notEmpty("la cosa no debe ser vacia")
}


fun String.notEmpty(message: String): String = this.ifEmpty { throw Exception(message) }
