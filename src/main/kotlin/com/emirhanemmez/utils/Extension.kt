package com.emirhanemmez.utils

import com.emirhanemmez.db.data.User
import com.emirhanemmez.error.exceptions.AuthenticationException
import com.papsign.ktor.openapigen.model.Described
import com.papsign.ktor.openapigen.model.security.HttpSecurityScheme
import com.papsign.ktor.openapigen.model.security.SecuritySchemeModel
import com.papsign.ktor.openapigen.model.security.SecuritySchemeType
import com.papsign.ktor.openapigen.modules.providers.AuthProvider
import com.papsign.ktor.openapigen.modules.providers.AuthProvider.Security
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.util.pipeline.*

inline fun <T> NormalOpenAPIRoute.auth(
    provider: AuthProvider<T>,
    crossinline route: OpenAPIAuthenticatedRoute<T>.() -> Unit = {}
): OpenAPIAuthenticatedRoute<T> {
    return provider.apply(this).apply {
        route()
    }
}

data class UserPrincipal(
    val userId: String,
    val email: String?,
    val name: String?,
    val roles: List<String>
) : Principal

class JwtProvider : AuthProvider<UserPrincipal> {
    override val security: Iterable<Iterable<Security<*>>>
        get() = listOf(
            listOf(
                Security(
                    SecuritySchemeModel(
                        SecuritySchemeType.http,
                        scheme = HttpSecurityScheme.bearer,
                        bearerFormat = "JWT",
                        name = "jwtAuth",
                        referenceName = "jwtAuthRef"
                    ),
                    emptyList<Scopes>()
                )
            )
        )

    override fun apply(route: NormalOpenAPIRoute): OpenAPIAuthenticatedRoute<UserPrincipal> {
        val authenticatedKtorRoute = route.ktorRoute.authenticate { }
        return OpenAPIAuthenticatedRoute(authenticatedKtorRoute, route.provider.child(), this)
    }

    override suspend fun getAuth(pipeline: PipelineContext<Unit, ApplicationCall>): UserPrincipal {
        return pipeline.context.authentication.principal() ?: throw AuthenticationException("No JWTPrincipal")
    }

}

enum class Scopes(override val description: String) : Described