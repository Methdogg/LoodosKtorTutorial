package com.emirhanemmez.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import java.util.*

class TokenManager {
    private val config = HoconApplicationConfig(ConfigFactory.load())

    private val audience = config.property("ktor.security.jwt.audience").getString()
    private val secret = config.property("ktor.security.jwt.secret").getString()
    private val issuer = config.property("ktor.security.jwt.issuer").getString()
    private val expirationPeriod = config.property("ktor.security.jwt.expiration_time").getString().toLong()
    private val algorithm = Algorithm.HMAC512(secret)

    fun generateToken(username: String): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(getExpirationTime())
            .sign(algorithm)

    private fun getExpirationTime() = Date(System.currentTimeMillis() + expirationPeriod)
}