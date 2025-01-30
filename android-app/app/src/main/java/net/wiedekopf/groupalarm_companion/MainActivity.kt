package net.wiedekopf.groupalarm_companion

import android.app.StatusBarManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import net.wiedekopf.groupalarm_companion.ui.theme.GroupAlarmCompanionTheme
import java.util.concurrent.Executor

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroupAlarmCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                requestTileCreation()
                            }, enabled = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        ) {
                            Text("Request tile creation")
                        }
                    }
                }
            }
        }
    }

    private fun requestTileCreation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val successExecutor = Executor {
                Log.d(TAG, "requestAddTileService result success")
            }
            val manager = application.getSystemService<StatusBarManager>()
            manager?.requestAddTileService(
                ComponentName.createRelative("quicksettings", "QsTileService"),
                "Title label",
                Icon.createWithResource(this, R.drawable.ic_android_black_24dp),
                successExecutor
            ) { resultCodeFailure ->
                Log.d(TAG, "requestAddTileService failure: resultCodeFailure: $resultCodeFailure")

            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupAlarmCompanionTheme {
        Greeting("Android")
    }
}