package net.wiedekopf.groupalarmcompanion.shared.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppSettings(private val context: Context) {
    private val personalAccessTokenKey = stringPreferencesKey("personalAccessToken")
    val personalAccessTokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[personalAccessTokenKey]
    }

    private val endpointKey = stringPreferencesKey("endpoint")
    val endpointFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[endpointKey]
    }

    suspend fun storeCredentials(endpoint: String, pat: String) {
        context.dataStore.edit { settings ->
            settings[personalAccessTokenKey] = pat
            settings[endpointKey] = endpoint
        }
    }

}

