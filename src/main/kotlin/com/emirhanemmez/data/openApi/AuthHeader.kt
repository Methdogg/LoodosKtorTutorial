package com.emirhanemmez.data.openApi

import com.papsign.ktor.openapigen.annotations.parameters.HeaderParam

data class AuthHeader(
    @HeaderParam("Auth Header Param") val jwtToken: String
)