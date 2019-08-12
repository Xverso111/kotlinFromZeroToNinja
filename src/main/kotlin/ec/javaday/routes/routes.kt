package ec.javaday.routes

import ec.javaday.domain.SearchCriteria
import ec.javaday.dto.IdResponse
import ec.javaday.service.TwitterService
import ec.javaday.validation.validUUID
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Route.routes() = route("/twitter/search") {

    val service: TwitterService by inject()

    post {
        val searchCriteria = call.receive<SearchCriteria>()
        val id = service.createQuery(searchCriteria)
        call.respond(HttpStatusCode.OK, IdResponse(id))
    }

    get("/{id}/top") {
        val uuid = call.parameters["id"].validUUID("The id is not valid")
        val topTweeters = service.topTweeters(uuid)
        call.respond(HttpStatusCode.OK, topTweeters)
    }
}

