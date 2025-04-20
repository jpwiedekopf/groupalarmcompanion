package gacompanion.mockserver

import gacompanion.mockserver.mock.data.MockOrganization
import gacompanion.lib.models.OrganizationAvailability
import kotlin.math.roundToInt
import kotlin.random.Random

class DataService {

    val user by lazy {
        User()
    }

    val orgAvailability by lazy {
        OrgAvailability()
    }

    class User {
        var isGloballyAvailable: Boolean = true
        var isOrganizationAvailable: Boolean = true
    }

    class OrgAvailability {
        private val total = MockOrganization.PERSONNEL_COUNT
        fun getCounts(): OrganizationAvailability.CountList {
            val proportionNotAvailable = Random.nextInt(from=1, until = 30) / 100.0
            val numberNotAvailable = (total * proportionNotAvailable).roundToInt()
            val proportionInEvent = Random.nextInt(from=0, until=50) / 100.0
            val numberInEvent = (total * proportionInEvent).roundToInt()
            val numberAvailable = total - numberNotAvailable - numberInEvent
            return OrganizationAvailability.CountList(
                countAvailable = numberAvailable,
                countInEvent = numberInEvent,
                countNotAvailable = numberNotAvailable
            )
        }
    }
}