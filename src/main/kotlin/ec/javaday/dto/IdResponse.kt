package ec.javaday.dto

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class IdResponse(val id: UUID)
