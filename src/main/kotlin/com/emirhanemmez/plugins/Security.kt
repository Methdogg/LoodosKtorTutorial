package com.emirhanemmez.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.config.*

fun Application.configureAuthentication() {
    authentication {
        jwt {
            val config = HoconApplicationConfig(ConfigFactory.load())
            realm = config.property("ktor.security.jwt.realm").getString()

            val secret = config.property("ktor.security.jwt.secret").getString()
            val issuer = config.property("ktor.security.jwt.issuer").getString()
            val audience = config.property("ktor.security.jwt.audience").getString()

            verifier(
                JWT.require(Algorithm.HMAC512(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (!credential.payload.getClaim("username").asString().isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}