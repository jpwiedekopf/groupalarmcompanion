package net.wiedekopf.gacompanion.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.compose.GaCompanionTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GaCompanionTheme(dynamicColor = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { appPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(appPadding)
                    ) {
                        Button(onClick = {}) {
                            Text(text = "Button")
                        }
                    }
                }
            }
        }
    }
}