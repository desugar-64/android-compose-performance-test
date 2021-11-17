package com.simple.tweet.plugins

import com.simple.tweet.data.TweetsApi
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/tweets") {
            call.respond(TweetsApi.list)
        }
    }
}
