package com.emirhanemmez

import io.ktor.application.*
import com.emirhanemmez.plugins.*
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File

fun main() {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load())

        val serverHost = config.property("ktor.deployment.host").getString()
        val serverPort = config.property("ktor.deployment.port").getString().toInt()
        val serverSSLPort = config.property("ktor.deployment.sslPort").getString().toInt()

        val keyAlias = config.property("ktor.security.ssl.keyAlias").getString()
        val keyPassword = config.property("ktor.security.ssl.keyPassword").getString()
        val jksPassword = config.property("ktor.security.ssl.jksPassword").getString()
        val keyStoreFile = File("build/keystore.jks")

        val keyStore = generateCertificate(
            file = keyStoreFile,
            keyAlias = keyAlias,
            keyPassword = keyPassword,
            jksPassword = jksPassword
        )

        connector {
            port = serverPort
            host = serverHost
        }

        sslConnector(
            keyStore = keyStore,
            keyAlias = keyAlias,
            keyStorePassword = { keyPassword.toCharArray() },
            privateKeyPassword = { jksPassword.toCharArray() },
        ) {
            port = serverSSLPort
            keyStorePath = keyStoreFile
            host = serverHost
        }
    }).start(wait = true)
}

fun Application.module() {
    configureAuthentication()
    configureKoin()
    configureRouting()
    configureSerialization()
    configureDatabase()
    configureErrorHandling()
    configureOpenApi()
}
