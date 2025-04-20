package gacompanion.mockserver.ktor

import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import gacompanion.mockserver.mock.data.MockUser

fun Application.configureSecurity() {
    install(Authentication) {
        patAuth(AUTH_SCHEME) {
            authenticate { credential ->
                return@authenticate when (credential.token == API_KEY) {
                    true -> MockUser.USER_ID
                    else -> null
                }
            }
        }
    }
}

class PatAuthProvider internal constructor(config: Config) :
    AuthenticationProvider(config) {

    private val authenticate = config.authenticate
    private val getAuthHeader = config.getAuthHeader
    private val schemesLowerCase = listOf(config.defaultScheme.lowercase())

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val log = context.call.application.log
        val authHeader = getAuthHeader(context.call) ?: let {
            log.info("Did not receive auth header $PAT_HEADER")
            context.call.respond(HttpStatusCode.Unauthorized)
            return
        }

        log.info("Got auth value '${authHeader.render()}'")

        val principal = (authHeader as? HttpAuthHeader.Single)
            ?.takeIf { it.authScheme.lowercase() in schemesLowerCase }
            ?.let { authenticate(context.call, BearerTokenCredential(it.blob)) }
            ?.also { log.info("Authenticated principal $it") }
            ?: run {
                log.info("Did not authenticate principal by API key")
                context.call.respond(HttpStatusCode.Unauthorized)
            }

        context.principal(principal)
    }

    class Config internal constructor(name: String?) : AuthenticationProvider.Config(name) {
        internal var authHeader: (ApplicationCall) -> String? = { call ->
            call.request.header(PAT_HEADER)
        }

        val getAuthHeader: (ApplicationCall) -> HttpAuthHeader? = { call ->
            when (val headerValue = authHeader(call)) {
                null -> null
                else -> HttpAuthHeader.Single(defaultScheme, blob = headerValue)
            }
        }

        internal var authenticate: AuthenticationFunction<BearerTokenCredential> = {
            throw NotImplementedError(
                "API Key auth authenticate function is not specified. Use apiKey { authenticate { ... } } to fix."
            )
        }

        internal val defaultScheme = AUTH_SCHEME

        fun authenticate(authenticate: suspend ApplicationCall.(BearerTokenCredential) -> Any?) {
            this.authenticate = authenticate
        }

        internal fun build() = PatAuthProvider(this)
    }
}

fun AuthenticationConfig.patAuth(
    name: String? = null,
    configure: PatAuthProvider.Config.() -> Unit,
) {
    val provider = PatAuthProvider.Config(name).apply(configure).build()
    register(provider)
}

const val PAT_HEADER = "Personal-Access-Token"

const val API_KEY = "hello-world"

const val AUTH_SCHEME = "auth-bearer"