package com.emirhanemmez.db.table

import com.emirhanemmez.db.data.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object UserTable : Table("user") {
    private val id = integer("id").autoIncrement()
    private val username = varchar("username", 20)
    private val password = text("password")

    override val primaryKey = PrimaryKey(id)

    fun addUser(user: User) = transaction {
        UserTable.insert {
            it[username] = user.username
            it[password] = user.password
        }
    }

    fun getUserList(): List<User> = transaction {
        UserTable.selectAll()
            .map {
                it.toUser()
            }.toList()
    }

    fun getUserById(id: Int): User = transaction {
        UserTable.select {
            this@UserTable.id eq id
        }.map {
            it.toUser()
        }.first()
    }

    fun updateUser(id: Int, user: User) = transaction {
        UserTable.update({
            this@UserTable.id eq id
        }) {
            it[username] = user.username
            it[password] = user.password
        }
    }

    fun deleteUser(id: Int) = transaction {
        UserTable.deleteWhere {
            this@UserTable.id eq id
        }
    }

    private fun ResultRow.toUser() =
        User(
            id = this[id],
            username = this[username],
            password = this[password]
        )
}