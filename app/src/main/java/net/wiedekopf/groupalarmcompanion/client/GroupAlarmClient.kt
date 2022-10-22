package net.wiedekopf.groupalarmcompanion.client

import android.content.Context
import kotlinx.coroutines.coroutineScope
import net.wiedekopf.groupalarmcompanion.model.Availability
import net.wiedekopf.groupalarmcompanion.model.OrganizationList
import net.wiedekopf.groupalarmcompanion.model.UserDetails
import net.wiedekopf.groupalarmcompanion.settings.AppSettings

abstract class GroupAlarmClient(val context: Context) {

    protected val settings = AppSettings(context)
    abstract suspend fun areStoredCredentialsPresentAndValid(): Boolean
    abstract suspend fun areCredentialsPresentAndValid(endpoint: String?, pat: String?): Boolean

    protected abstract fun getCurrentOrganizationList(): OrganizationList

    fun getOrganizationList(): OrganizationList {
        return getCurrentOrganizationList()
        // TODO: implement caching, e.g. with https://github.com/crypticminds/ColdStorage;
        // since this doesn't change terribly often!
    }

    abstract fun getUser(): UserDetails

    abstract fun getAvailabilities(): List<Availability>
    abstract fun setGlobalAvailability(isAvailable: Boolean)
    abstract fun setOrgAvailability(isAvailable: Boolean, orgId: String)

    suspend fun storeCredentials(endpointUri: String, pat: String) {
        settings.storeCredentials(endpoint = endpointUri, pat = pat)
    }
}