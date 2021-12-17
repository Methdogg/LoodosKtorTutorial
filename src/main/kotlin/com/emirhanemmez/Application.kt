package com.emirhanemmez

import io.ktor.application.*
import com.emirhanemmez.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "localhost") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureAuthentication()
    configureRouting()
    configureSerialization()
    configureDatabase()
}
