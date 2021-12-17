package com.emirhanemmez.plugins

import com.emirhanemmez.error.exceptions.AuthenticationException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<AuthenticationException> { cause ->
            call.respond(status = HttpStatusCode.Unauthorized, message = cause.message ?: "Authentication failed!")
        }
        exception<BadRequestException> { cause ->
            call.respond(status = HttpStatusCode.BadRequest, message = cause.message ?: "Bad request!")
        }
    }
}