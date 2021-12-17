package com.emirhanemmez.error.exceptions

data class AuthenticationException(
    override val message: String? = null,
    override val cause: Throwable? = null
): Throwable(message, cause)
