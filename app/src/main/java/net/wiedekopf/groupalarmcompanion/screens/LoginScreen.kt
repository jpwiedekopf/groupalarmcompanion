package net.wiedekopf.groupalarmcompanion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.wiedekopf.groupalarmcompanion.R
import net.wiedekopf.groupalarmcompanion.shared.client.MockClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    client: MockClient, padding: PaddingValues, onCredentialsValid: () -> Unit
) {
    val defaultEndpointValue = stringResource(id = R.string.default_endpoint)
    val (endpointUri, setEndpointUri) = remember {
        mutableStateOf(defaultEndpointValue)
    }
    val (pat, setPat) = remember {
        mutableStateOf("")
    }
    var credentialsVerified by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = endpointUri,
            onValueChange = setEndpointUri,
            label = {
                Text(stringResource(id = R.string.endpoint))
            },
            placeholder = {
                Text(
                    defaultEndpointValue,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            },
            trailingIcon = {
                IconButton(onClick = { setEndpointUri(defaultEndpointValue) }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.reset)
                    )
                }
            })

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = pat,
            onValueChange = setPat,
            label = {
                Text(stringResource(id = R.string.personal_access_token))
            })

        Button(onClick = {
            if (credentialsVerified) {
                coroutineScope.launch {
                    client.storeCredentials(endpointUri = endpointUri, pat = pat)
                }
                onCredentialsValid()
            }
        }, enabled = credentialsVerified) {
            Icon(painterResource(R.drawable.save_48px), contentDescription = null)
            Text(text = stringResource(id = R.string.save))
        }
    }

    LaunchedEffect(key1 = endpointUri, key2 = pat) {
        credentialsVerified = client.areCredentialsPresentAndValid(endpointUri, pat)
    }
}