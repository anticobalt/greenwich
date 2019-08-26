package musubidevs.android.greenwich.model

import org.joda.time.DateTimeZone
import java.io.Serializable

/**
 * Has a FROM date/time/timezone and TO date/time/timezone.
 *
 * @author jmmxp
 */
data class Conversion(
    var sourceTimestamp: SourceTimestamp = SourceTimestamp(),
    var targetTimestamp: TargetTimestamp = TargetTimestamp()
): Serializable {

    init {
        targetTimestamp = targetTimestamp.withOffset(UtcOffset.from(DateTimeZone.UTC))
    }

    fun updateTimestamp(timestamp: Timestamp) {
        when (timestamp) {
            is SourceTimestamp -> {
                sourceTimestamp = timestamp
                targetTimestamp = targetTimestamp.withSource(sourceTimestamp)
            }
            is TargetTimestamp -> targetTimestamp = timestamp
            else -> throw ClassNotFoundException("${timestamp.javaClass.name} is not a valid Timestamp type")
        }
    }
}