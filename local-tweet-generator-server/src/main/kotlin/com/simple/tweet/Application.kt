package com.simple.tweet

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.simple.tweet.plugins.*
import io.ktor.features.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        configureHTTP()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
