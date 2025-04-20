package net.wiedekopf.gacompanion.android.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream

//object AppSettingsSerializer : Serializer<AppSettings> {
//    override val defaultValue: Settings = Settings.getDefaultInstance()
//
//    override suspend fun readFrom(input: InputStream): AppSettings {
//        try {
//            return Settings.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//    }
//
//    override suspend fun writeTo(
//        t: AppSettings,
//        output: OutputStream
//    ) = t.writeTo(output)
//}
//
//val Context.appSettingsDataStore: DataStore<AppSettings> by dataStore(
//    fileName = "app_settings.proto",
//    serializer = AppSettingsSerializer
//)