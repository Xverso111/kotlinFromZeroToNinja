package ec.javaday.service

import ec.javaday.adapter.QueryAdapter
import ec.javaday.domain.SearchCriteria
import ec.javaday.dto.TopTweetResponse
import ec.javaday.exception.ResourceNotFoundException
import ec.javaday.repository.SearchCriteriaRepository
import ec.javaday.twitter.TwitterClient
import java.util.*

class TwitterService(
    private val twitterClient: TwitterClient,
    private val searchCriteriaRepository: SearchCriteriaRepository
) {

    fun createQuery(searchCriteria: SearchCriteria) = searchCriteriaRepository.insert(searchCriteria)

    fun topTweeters(uuid: UUID): List<TopTweetResponse> {
        val topTweetersQuery = searchCriteriaRepository.getById(uuid) ?: throw ResourceNotFoundException("Query not found")
        val query = QueryAdapter(topTweetersQuery).query
        val retrievedTweets = twitterClient.searchByQuery(query)
        return retrievedTweets
            .filter { topTweetersQuery.dateRange?.contains(it.tweetedDate) ?: true }
            .groupBy { it.userName }
            .map { TopTweetResponse(it.key, it.value.size) }
    }
}
