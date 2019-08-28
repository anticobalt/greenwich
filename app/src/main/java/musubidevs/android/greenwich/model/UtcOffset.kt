package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.abs

/**
 * Represents a timezone as an offset of UTC.
 *
 * @author jmmxp
 */
class UtcOffset(private val hours: Int, private val minutes: Int) {
    companion object {
        const val minusSign = '−'
        const val plusSign = '+'
        const val plusMinusSign = '±'

        fun default(): UtcOffset {
            return from(DateTimeZone.getDefault())
        }

        fun from(dateTimeZone: DateTimeZone): UtcOffset {
            val instant = DateTime.now(dateTimeZone)
            val offsetMillis = dateTimeZone.getOffset(instant).toLong()
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

    override fun toString(): String {
        if (hours == 0 && minutes == 0) return "UTC${plusMinusSign}0"

        var res = "UTC"
        res += if (hours > 0 && minutes >= 0) {
            plusSign
        } else {
            minusSign
        }
        res += abs(hours).toString()
        if (minutes != 0) {
            res += ":${abs(minutes)}"
        }
        return res
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is UtcOffset) false
        else this.hours == other.hours && this.minutes == other.minutes
    }

    override fun hashCode(): Int {
        var result = hours
        result = 31 * result + minutes
        return result
    }
}