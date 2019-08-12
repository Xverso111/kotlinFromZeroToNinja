package ec.javaday.domain

import com.squareup.moshi.JsonClass
import ec.javaday.validation.notBlank
import ec.javaday.validation.notEmpty

@JsonClass(generateAdapter = true)
class SearchCriteria(
    name: String,
    hashTags: List<String>,
    val dateRange: DateRange? = null,
    val allowRetweets: Boolean = false
) {

    val hashTags = hashTags
        .notEmpty("Cannot create a SearchCriteria without hashTags")

    val name = name
        .notBlank("Cannot create a SearchCriteria without a name")
}
