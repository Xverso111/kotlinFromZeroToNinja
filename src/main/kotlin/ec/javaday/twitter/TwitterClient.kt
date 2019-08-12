package ec.javaday.twitter

import ec.javaday.domain.Tweet
import org.joda.time.LocalDateTime
import twitter4j.Query
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory

class TwitterClient {

    private val twitter = TwitterFactory.getSingleton()

    private fun Twitter.query(query: Query) = Iterable { TwitterQueryIterator(this, query) }

    private fun Status.toTweet() =
        Tweet(
            this.id,
            this.user.screenName,
            this.text,
            LocalDateTime(this.createdAt.toInstant().toEpochMilli())
        )

    fun searchByQuery(query: Query) =
        twitter
            .query(query)
            .flatMap { it.tweets }
            .map { it.toTweet() }

}
