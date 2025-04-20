package gacompanion.lib.uris

import io.ktor.http.HttpMethod
import java.net.URI

const val DEFAULT_GA_ENDPOINT = "https://app.groupalarm.com"

class UriBuilder(
    val baseUrl: String = DEFAULT_GA_ENDPOINT
) {

    enum class BaseUriValidationResult {
        Valid,
        NotAnUri,
        MissingProtocol,
        NoValue
    }

    fun isValidBaseUri(): BaseUriValidationResult {
        if (baseUrl.isBlank()) {
            return BaseUriValidationResult.NoValue
        }
        try {
            URI.create(baseUrl)
        } catch (_: IllegalArgumentException) {
            return BaseUriValidationResult.NotAnUri
        }
        if (!baseUrl.startsWith("http") || ! baseUrl.contains("://")) {
            return BaseUriValidationResult.MissingProtocol
        }
        return BaseUriValidationResult.Valid
    }

    fun buildUri(gaApiEndpoint: GaApiEndpoint) = buildString {
        append(baseUrl.trimEnd('/'))
        append("/")
        append(gaApiEndpoint.relativeUri.trimStart('/'))
    }

}

sealed class GaApiEndpoint(
    val relativeUri: String,
    val verbs: List<HttpMethod>
) {
    object SetupApiKey : GaApiEndpoint(
        relativeUri = "/d/profile/settings",
        verbs = listOf(HttpMethod.Get)
    )
}

