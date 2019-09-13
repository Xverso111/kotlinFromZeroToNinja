package ec.javaday.application

import com.ryanharter.ktor.moshi.moshi
import com.squareup.moshi.JsonDataException
import ec.javaday.domain.SearchCriteria
import ec.javaday.exception.BusinessRuleException
import ec.javaday.moshiWithAdapters
import ec.javaday.objectMapper
import ec.javaday.repository.SearchCriteriaRepository
import ec.javaday.service.TwitterService
import ec.javaday.twitter.TwitterClient
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import java.util.*

fun Application.start() {
    val repository = SearchCriteriaRepository()
     val twitterClient = TwitterClient()
     val twitterService = TwitterService(repository,twitterClient)

    //moshi is nullable safe
    //no usa reflection
    install(ContentNegotiation) {
        moshi(moshiWithAdapters)
    }

    install(StatusPages) {
        exception<Throwable> {
            val message = it.message ?: "Unknown error"
            val code = when (it) {
                is JsonDataException,
                is BusinessRuleException -> HttpStatusCode.BadRequest
                else -> HttpStatusCode.InternalServerError
            }
            call.respond(code, message)
        }
    }
    routing {
        route("/twitter/search") {
            post {
                val searchCriteria = call.receive<SearchCriteria>()
                val id = twitterService.createSearchCriteria(searchCriteria)
                call.respond(HttpStatusCode.Created, IdResponse(id))
            }

            post("xconf") {
                val searchCriteria = call.receive<SearchCriteria>()
                val response = twitterService.question(searchCriteria)
                call.respondText(objectMapper.writeValueAsString(response), ContentType("application", "json"), HttpStatusCode.OK)
            }

            // Parametro iD -> {id}
            get("/{id}/top") {
                val uuid = call.parameters["id"].validUUID("The id is not valid")
                val topTweeters = twitterService.topTweeters(uuid)
                call.respond(HttpStatusCode.OK, topTweeters.toString())
            }
        }


    }
}

// Valida y convierte a UUID
fun String?.validUUID(message: String): UUID =
    // Try Catch como expresión!!!
    // Retorna la última linea
    try {
        UUID.fromString(this)
    } catch (exception: Exception) {
        throw BusinessRuleException(message)
    }

data class IdResponse(val id: UUID)
