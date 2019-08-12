package ec.javaday.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(val message: String, val exception: String)
