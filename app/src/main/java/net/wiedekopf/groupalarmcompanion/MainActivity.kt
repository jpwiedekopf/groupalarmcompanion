package net.wiedekopf.groupalarmcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.wiedekopf.groupalarmcompanion.screens.LoginScreenContent
import net.wiedekopf.groupalarmcompanion.shared.client.MockClient
import net.wiedekopf.groupalarmcompanion.screens.MainAppScreen
import net.wiedekopf.groupalarmcompanion.ui.theme.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val client = remember {
                MockClient(context = context)
            }
            var globalAvailability by remember {
                mutableStateOf(false)
            }
            rememberCoroutineScope()
            val (credentialsValid, setCredentialsValid) = remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = credentialsValid) {
                val cred = client.areStoredCredentialsPresentAndValid()
                setCredentialsValid(cred)
            }
            GroupAlarmCompanionTheme(dynamicColor = false) {
                Scaffold(bottomBar = {
                    if (credentialsValid) AppBottomBar(globalAvailability) {
                        globalAvailability = !globalAvailability
                    }
                }) { padding ->
                    when (credentialsValid) {
                        true -> AppContent(client, padding)
                        false -> LoginScreenContent(client, padding) {
                            setCredentialsValid(true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBottomBar(globalAvailability: Boolean, toggleGlobalAvailability: () -> Unit) {
    val availableColor = getAvailableColors()
    val unavailableColor = getUnavailableColors()
    BottomAppBar(actions = {}, floatingActionButton = {
        FloatingActionButton(
            onClick = toggleGlobalAvailability, containerColor = when (globalAvailability) {
                true -> availableColor.container
                false -> unavailableColor.container
            }
        ) {

            when (globalAvailability) {
                true -> Icon(
                    painter = painterResource(id = R.drawable.notifications_active_48px),
                    tint = availableColor.onContainer,
                    contentDescription = stringResource(
                        id = R.string.notifications_active
                    )
                )
                else -> Icon(
                    painter = painterResource(id = R.drawable.notifications_off_48px),
                    tint = unavailableColor.onContainer,
                    contentDescription = stringResource(
                        id = R.string.notifications_disabled
                    )
                )
            }
        }
    })
}

@Composable
fun AppContent(client: MockClient, padding: PaddingValues) {
    Column(
        Modifier.padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MainAppScreen(client = client)
    }
}