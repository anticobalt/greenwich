package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import kotlin.math.abs

/**
 * @author jmmxp
 */
class TargetTimestamp(
    dateTime: DateTime = DateTime.now(),
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
) : Timestamp(dateTime, dateTimeZone) {
    fun withSource(sourceTimestamp: SourceTimestamp): TargetTimestamp {
        val offset = utcOffset - sourceTimestamp.utcOffset

        val sourceDateTime = sourceTimestamp.dateTime
        val newTargetDateTime = if (offset < 0) sourceDateTime.minus(abs(offset) * 3600000)
        else sourceDateTime.plus(offset * 3600000)

        return TargetTimestamp(newTargetDateTime, dateTimeZone)
    }
}