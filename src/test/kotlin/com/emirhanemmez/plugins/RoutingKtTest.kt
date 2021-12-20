package com.emirhanemmez.plugins

import com.emirhanemmez.db.data.User
import com.emirhanemmez.module
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.http.ContentType.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutingKtTest {

    @Test
    fun `get user by id`() {
        withTestApplication({ module() }) {
            handleRequestWithToken(HttpMethod.Get, "/user/1") {
                addHeader(HttpHeaders.UserAgent, "EMIRHAN-PC")
            }.apply {
                assertEquals("EMIRHAN-PC", request.headers[HttpHeaders.UserAgent])
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}

fun TestApplicationEngine.handleRequestWithToken(
    method: HttpMethod,
    uri: String,
    setup: TestApplicationRequest.() -> Unit = {}
): TestApplicationCall {
    val user = User(0, "emirhan", "1234")
    val userBody = jacksonObjectMapper().writeValueAsString(user)

    val token = handleRequest(HttpMethod.Post, "/login") {
        addHeader(HttpHeaders.ContentType, Application.Json.toString())
        addHeader(HttpHeaders.Accept, Application.Json.toString())
        setBody(userBody)
    }.response.content

    val requestSetup: TestApplicationRequest.() -> Unit = {
        addHeader(HttpHeaders.ContentType, Application.Json.toString())
        addHeader(HttpHeaders.Accept, Application.Json.toString())
        addHeader(HttpHeaders.Authorization, "Bearer $token")
        setup.invoke(this)
    }
    return handleRequest(method, uri, requestSetup)
}
