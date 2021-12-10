package com.emirhanemmez.plugins

import com.emirhanemmez.db.data.User
import com.emirhanemmez.db.table.UserTable
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello world")
        }

        route("/user") {
            get {
                call.respond(UserTable.getUserList())
            }

            get("/{index}") {
                val index = call.parameters["index"]?.toInt()
                index?.let {
                    call.respond(UserTable.getUserById(it))
                }
            }

            post {
                val userBody = call.receive<User>()
                UserTable.addUser(userBody)
                call.respondText("User successfully created!")
            }

            put("/{index}") {
                val index = call.parameters["index"]?.toInt()
                val userBody = call.receive<User>()
                index?.let {
                    UserTable.updateUser(it, userBody)
                    call.respond("Successfully updated!")
                }
            }

            delete("/{index}") {
                val index = call.parameters["index"]?.toInt()
                index?.let {
                    UserTable.deleteUser(it)
                    call.respondText { "Successfully deleted" }
                }
            }
        }
    }
}
