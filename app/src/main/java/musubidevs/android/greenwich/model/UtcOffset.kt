package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class UtcOffset(private val hours: Int, private val minutes: Int) {
    companion object {
        fun default(): UtcOffset {
            val instant = DateTime.now().millis
            val offsetMillis = DateTimeZone.getDefault().getOffset(instant).toLong()
            val offsetHours = TimeUnit.MILLISECONDS.toHours(offsetMillis).toInt()
            val offsetMinutes = TimeUnit.MILLISECONDS.toMinutes(offsetMillis % 3600000).toInt()
            return UtcOffset(offsetHours, offsetMinutes)
        }
    }

    fun toMillis(): Long {
        var millis = 0L
        millis += abs(hours) * 3600000
        millis += abs(minutes) * 60000
        return if (hours < 0 || minutes < 0) -millis else millis
    }

    fun toLongString(): String {
        if (hours == 0 && minutes == 0) {
            return "±00:00"
        }

        val hourStr = abs(hours).toString().padStart(2, '0')
        val minStr = abs(minutes).toString().padStart(2, '0')

        return if (hours > 0 && minutes >= 0) "+$hourStr:$minStr" else "-$hourStr:$minStr"
    }

    override fun toString(): String {
        var res = ""
        if (hours > 0 && minutes >= 0) {
            res += "+"
        }
        res += hours.toString()
        if (minutes != 0) {
            res += ":${abs(minutes)}"
        }
        return res
    }
}