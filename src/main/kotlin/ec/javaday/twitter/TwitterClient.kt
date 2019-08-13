package ec.javaday.twitter

import org.joda.time.LocalDateTime
import twitter4j.Query
import twitter4j.Status
import twitter4j.TwitterFactory

// Twitter4J!!!
// Interoperable con Java
class TwitterClient() {
    private val twitter = TwitterFactory.getSingleton()

    // Query -> Twitter4J
    fun search(query: Query) =
        twitter
            .query(query)
            //List<List<Status>>
            .flatMap { it.tweets }
            //A Tweet
            .map { it.toTweet() }

    private fun Status.toTweet() =
        Tweet(
            this.user.screenName,
            LocalDateTime(this.createdAt.toInstant().toEpochMilli())
        )

}

class Tweet(
    val userName: String,
    val tweetedDate: LocalDateTime
)
