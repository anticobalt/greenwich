package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * @author jmmxp
 */
class SourceTimestamp(
    dateTime: DateTime = DateTime.now(),
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
) : Timestamp(dateTime, dateTimeZone) {
    fun withDate(year: Int, month: Int, day: Int): SourceTimestamp {
        return SourceTimestamp(dateTime.withDate(year, month, day), dateTimeZone)
    }

    fun withTime(hour: Int, minute: Int): SourceTimestamp {
        return SourceTimestamp(dateTime.withTime(hour, minute, 0, 0), dateTimeZone)
    }
}