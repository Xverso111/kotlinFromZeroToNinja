package ec.javaday.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopTweetResponse(val name: String, val count: Int)
