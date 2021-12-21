package com.emirhanemmez.plugins

import com.emirhanemmez.data.openApi.AuthHeader
import com.emirhanemmez.db.data.User
import com.emirhanemmez.utils.JwtProvider
import com.emirhanemmez.utils.auth
import com.papsign.ktor.openapigen.APITag
import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.status
import com.papsign.ktor.openapigen.route.tag
import io.ktor.application.*
import io.ktor.http.*

fun Application.configureOpenApi() {
    install(OpenAPIGen) {
        serveSwaggerUi = true
        swaggerUiPath = "/swagger-ui"
        info {
            version = "0.0.1"
            title = "Loodos Tutorial API"
            description = "Loodos Tutorial API"
        }

        server("http://localhost:8081/") {
            description = "Local Server"
        }
    }

    apiRouting {
        tag(object : APITag {
            override val description: String
                get() = "User Methods"
            override val name: String
                get() = "User"

        }) {
            val jwtAuthProvider = JwtProvider()
            auth(jwtAuthProvider) {
                route("/user") {
                    get<AuthHeader, List<User>>(
                        info(
                            summary = "Get all users",
                            description = "Return a list of all users"
                        ),
                        status(HttpStatusCode.OK),
                        example = listOf(User(1, "emir", "1234"), User(2, "emirhan", "5678")),
                    ) {
                        respond(listOf(User(1, "emir", "1234"), User(2, "emirhan", "5678")))
                    }
                }
            }
        }
    }
}