package com.emirhanemmez.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    val userList = mutableListOf("emirhan", "ahmet", "mehmet", "zeynep")

    routing {
        get("/") {
            call.respondText("Hello world")
        }

        route("/user") {
            get {
                call.respond(userList)
            }

            get("/{index}") {
                val index = call.parameters["index"]?.toInt()
                index?.let {
                    call.respondText(userList[index])
                }
            }

            post {
                val body = call.receive<String>()
                userList.add(body)
                call.respond(userList)
            }

            put {
                val body = call.receive<String>()
                userList.add(body)
                call.respond(status = HttpStatusCode.Created, userList)
            }

            delete("/{index}") {
                val index = call.parameters["index"]?.toInt()
                if (index != null) {
                    if (userList.indices.contains(index)) {
                        userList.removeAt(index)
                        call.respond(status = HttpStatusCode.OK, userList)
                    } else {
                        call.respondText(status = HttpStatusCode.NotFound, text = "The person in ${index}. index does not exist")
                    }
                } else {
                    call.respondText(status = HttpStatusCode.BadRequest, text = "Wrong index entered!")
                }
            }
        }
    }
}
