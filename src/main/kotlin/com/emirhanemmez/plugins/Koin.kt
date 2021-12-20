package com.emirhanemmez.plugins

import com.emirhanemmez.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin


fun Application.configureKoin() {
    install(Koin) {
        modules(module {
            single {
                HoconApplicationConfig(ConfigFactory.load())
            }
            single { TokenManager(get()) }
        })
    }
}