package ec.javaday.domain

import org.joda.time.LocalDateTime

class Tweet(
    val userId: Long,
    val userName: String,
    val text: String,
    val tweetedDate: LocalDateTime
)
