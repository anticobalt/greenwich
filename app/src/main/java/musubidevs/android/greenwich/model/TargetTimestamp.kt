package musubidevs.android.greenwich.model

import org.joda.time.DateTime
import java.io.Serializable
import kotlin.math.abs

/**
 * @author jmmxp
 * @author anticobalt
 */
class TargetTimestamp(
    dateTime: DateTime = DateTime.now(),
    utcOffset: UtcOffset = UtcOffset.default()
) : Timestamp(dateTime, utcOffset), Serializable {
    fun withSource(sourceTimestamp: SourceTimestamp): TargetTimestamp {
        val offset = utcOffset.toMillis() - sourceTimestamp.utcOffsetMillis

        val sourceDateTime = sourceTimestamp.dateTime
        val newTargetDateTime = if (offset < 0) sourceDateTime.minus(abs(offset))
        else sourceDateTime.plus(offset)

        return TargetTimestamp(newTargetDateTime, utcOffset)
    }

    override fun withOffset(utcOffset: UtcOffset): TargetTimestamp {
        val difference =  utcOffset.toMillis() - this.utcOffset.toMillis()
        val newDateTime = if (difference < 0) dateTime.minus(abs(difference)) else dateTime.plus(difference)
        return TargetTimestamp(newDateTime, utcOffset)
    }
}
