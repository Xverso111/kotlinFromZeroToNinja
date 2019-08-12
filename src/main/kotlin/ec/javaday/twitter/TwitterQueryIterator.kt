package ec.javaday.twitter

import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Twitter

class TwitterQueryIterator(
    private val twitter: Twitter,
    query: Query
): Iterator<QueryResult> {

    private var currentQuery: Query? = query

    override fun hasNext() = currentQuery != null

    override fun next(): QueryResult {
        val queryResult = twitter.search(currentQuery)
        currentQuery = queryResult.nextQuery()
        return queryResult
    }
}
