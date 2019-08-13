package ec.javaday.service

import ec.javaday.adapter.toQuery
import ec.javaday.domain.SearchCriteria
import ec.javaday.exception.ResourceNotFoundException
import ec.javaday.repository.SearchCriteriaRepository
import ec.javaday.twitter.TwitterClient
import java.util.*

class TwitterService(
    //atributo private
    private val searchCriteriaRepository: SearchCriteriaRepository,
    private val twitterClient: TwitterClient
) {
    fun createSearchCriteria(searchCriteria: SearchCriteria) = searchCriteriaRepository.insert(searchCriteria)

    fun topTweeters(uuid: UUID): Map<String, Int> {
        val searchCriteria = searchCriteriaRepository.getById(uuid)
        // Elvis!!!! -> ?:
        // Ejecuta lo de la derecha si es nulo
            ?: throw ResourceNotFoundException("Query not found")

        //
        val retrievedTweets = twitterClient.search(searchCriteria.toQuery())

        return retrievedTweets
                // Filtra por fecha y hora
            .filter { searchCriteria.dateRange?.contains(it.tweetedDate) ?: true }
            // Agrupa por nombre
            .groupBy { it.userName }
                // Mapea
            .mapValues { it.value.size }
    }
}
