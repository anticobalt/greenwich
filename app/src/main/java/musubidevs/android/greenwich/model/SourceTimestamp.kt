package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import java.io.Serializable

/**
 * @author jmmxp
 */
class SourceTimestamp(
    dateTime: DateTime = DateTime.now(),
    utcOffset: UtcOffset = UtcOffset.default()
) : Timestamp(dateTime, utcOffset), Serializable {

    fun withDate(year: Int, month: Int, day: Int): SourceTimestamp {
        return SourceTimestamp(dateTime.withDate(year, month, day), utcOffset)
    }

    fun withTime(hour: Int, minute: Int): SourceTimestamp {
        return SourceTimestamp(dateTime.withTime(hour, minute, 0, 0), utcOffset)
    }

    override fun withOffset(utcOffset: UtcOffset): Timestamp {
        return SourceTimestamp(dateTime, utcOffset)
    }
}