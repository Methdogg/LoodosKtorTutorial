package com.emirhanemmez.db.data

import at.favre.lib.crypto.bcrypt.BCrypt

data class User(
    val id: Int,
    val username: String,
    var password: String
) {
    fun hashedPassword(): String = BCrypt.withDefaults().hashToString(12, password.toCharArray())
}
