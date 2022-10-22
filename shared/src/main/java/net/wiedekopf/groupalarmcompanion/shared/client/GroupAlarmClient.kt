package net.wiedekopf.groupalarmcompanion.shared.client

import android.content.Context
import net.wiedekopf.groupalarmcompanion.shared.model.Availability
import net.wiedekopf.groupalarmcompanion.shared.model.OrganizationList
import net.wiedekopf.groupalarmcompanion.shared.model.UserDetails
import net.wiedekopf.groupalarmcompanion.shared.settings.AppSettings

abstract class GroupAlarmClient(context: Context) {

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