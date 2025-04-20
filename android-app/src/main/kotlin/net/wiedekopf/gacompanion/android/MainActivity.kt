package net.wiedekopf.gacompanion.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.GaCompanionTheme
import kotlinx.serialization.Serializable
import net.wiedekopf.gacompanion.android.ui.screens.SetupScreen

private const val TAG = "MainActivity"

@Serializable
object Home

@Serializable
object Setup


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GaCompanionTheme(dynamicColor = true) {
                NavHost(navController, startDestination = Setup) {
                    composable<Home> {

                    }
                    composable<Setup> {
                        SetupScreen()
                    }
                }

            }
        }
    }
}