package ec.javaday.serializer

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import java.util.*

class UUIDAdapter {

    @ToJson
    fun toJson(uuid: UUID) = uuid.toString()

    @FromJson
    fun fromJson(uuid: String) = try {
        UUID.fromString(uuid)
    } catch (exception: Exception) {
        throw JsonDataException("Incorrect UUID")
    }
}
