package ec.javaday.adapter

import ec.javaday.domain.SearchCriteria
import twitter4j.Query

/**
 * Creates a Query object to be used with twitter4japi
 * Query object receives a string that uses query format
 * "#hashtag OPERATOR #hashtag since:date until:date [additional arguments like -filter:retweets]"
**/
// No esta dentro de una Clase
// Solo un archivo
// Adapter
fun SearchCriteria.toQuery(): Query {
    val filter = if(this.allowRetweets) "" else "-filter:retweets"
    val hashTags = this.hashtags.map { "#$it" }.reduce { acc, hashtag -> "$acc AND $hashtag" }

    return Query("$hashTags $filter").apply {
        dateRange?.let {
            since = it.start.toString("YYYY-MM-dd")
            until = it.end.toString("YYYY-MM-dd")
        }
    }
}
