package musubidevs.android.greenwich.helper

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import musubidevs.android.greenwich.item.TimeZoneItem
import musubidevs.android.greenwich.model.UtcOffset
import org.joda.time.DateTimeZone

/**
 * Gets all TZ database timezones that
 *  1) are not deprecated,
 *  2) do not belong to the Etc region, as they are redundant, and
 *  3) are not aliases for Etc timezones (e.g. UTC).
 *
 * Timezone strings are also reformatted.
 *
 * @author anticobalt
 * @author jmmxp
 */
class TimeZoneFetcher {
    var timeZoneItems = mutableListOf<TimeZoneItem>()

    @SuppressLint("DefaultLocale")
    fun fetch(): TimeZoneFetcher {
        GlobalScope.launch(Dispatchers.IO) {
            timeZoneItems.addAll(DateTimeZone.getAvailableIDs().toList().filter {
                val id = it.toLowerCase()
                isSettlement(id) && !isEtc(id) && !isCountryDeprecated(id)
            }.map {
                TimeZoneItem(
                    removeUnderscores(it),
                    UtcOffset.from(DateTimeZone.forID(it)).toString()
                )
            })
        }

        return this
    }

    private fun removeUnderscores(id: String): String {
        return id.replace("_", " ")
    }

    private fun isSettlement(id: String): Boolean {
        return id.contains("/")
    }

    private fun isEtc(id: String): Boolean {
        return id.startsWith("etc")
    }

    private fun isCountryDeprecated(id: String): Boolean {
        val deprecatedCountries = setOf("australia", "canada", "brazil", "mexico", "us", "chile")
        return deprecatedCountries.any { country -> id.startsWith(country) }
    }
}