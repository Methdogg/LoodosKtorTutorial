package com.emirhanemmez.plugins;

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test

public class RoutingKtTest {

    @Test
    fun testGet() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                TODO("Please write your test here")
            }
        }
    }
}