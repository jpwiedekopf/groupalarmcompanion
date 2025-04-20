package net.wiedekopf.gacompanion.android.ui.screens

import net.wiedekopf.gacompanion.android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import gacompanion.lib.uris.DEFAULT_GA_ENDPOINT
import gacompanion.lib.uris.GaApiEndpoint
import gacompanion.lib.uris.UriBuilder
import gacompanion.lib.uris.UriBuilder.BaseUriValidationResult


@Composable
fun SetupScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { appPadding ->
        val apiKey = remember { mutableStateOf("") }
        val apiEndpoint = remember { mutableStateOf(DEFAULT_GA_ENDPOINT) }
        val uriBuilder by derivedStateOf {
            UriBuilder(baseUrl = apiEndpoint.value)
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(appPadding)
                .padding(horizontal = 8.dp)
                .padding(top = 12.dp)
                .background(color = colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
            )

            SetupUi(
                apiKey = apiKey,
                apiEndpoint = apiEndpoint,
                uriBuilder = uriBuilder,
            )

            Button(onClick = {

            }) {
                Text("Test connection")
            }

        }
    }
}

@Composable
fun ColumnScope.SetupUi(
    apiKey: MutableState<String>,
    apiEndpoint: MutableState<String>,
    uriBuilder: UriBuilder
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ApiKeyEntry(apiKey, uriBuilder)
        EndpointEntry(apiEndpoint, uriBuilder)
    }

}

@Composable
private fun ApiKeyEntry(apiKey: MutableState<String>, uriBuilder: UriBuilder) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = apiKey.value,
            onValueChange = { apiKey.value = it },
            label = { Text("API Key") },
            placeholder = { Text("API Key") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = buildAnnotatedString {
            append("To setup your API key, click ")
            pushStringAnnotation(
                tag = "setup",
                annotation = uriBuilder.buildUri(GaApiEndpoint.SetupApiKey)
            )
            withStyle(SpanStyle(color = colorScheme.primary)) {
                append("here")
            }
            pop()
            append(".")
        })
    }
}

@Composable
fun EndpointEntry(apiEndpoint: MutableState<String>, uriBuilder: UriBuilder) {
    val validationError by derivedStateOf { uriBuilder.isValidBaseUri() }
    val stringResourceId by derivedStateOf {
        when (validationError) {
            BaseUriValidationResult.Valid -> null
            BaseUriValidationResult.MissingProtocol -> R.string.uri_missing_protocol
            BaseUriValidationResult.NotAnUri -> R.string.uri_not_an_uri
            BaseUriValidationResult.NoValue -> null
        }
    }
    OutlinedTextField(
        value = apiEndpoint.value,
        onValueChange = { apiEndpoint.value = it },
        label = { Text("API Endpoint") },
        placeholder = { Text(DEFAULT_GA_ENDPOINT) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        isError = validationError != BaseUriValidationResult.Valid,
        trailingIcon = {
            IconButton(onClick = { apiEndpoint.value = DEFAULT_GA_ENDPOINT }) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.reset)
                )
            }
        }
    )
    stringResourceId?.let {
        Text(
            text = stringResource(it),
            color = colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}