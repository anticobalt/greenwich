package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * @author jmmxp
 */
class TargetTimestamp(
    dateTime: DateTime = DateTime.now(),
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
) : Timestamp(dateTime, dateTimeZone) {
    fun withSource(sourceTimestamp: SourceTimestamp): TargetTimestamp {
        val offset = utcOffset - sourceTimestamp.utcOffset
        return TargetTimestamp(dateTime.minus(offset), dateTimeZone)
    }
}