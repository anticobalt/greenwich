package musubidevs.android.greenwich.model

import android.annotation.SuppressLint
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * @author anticobalt
 * @author jmmxp
 */
abstract class Timestamp(
    protected val dateTime: DateTime,
    protected val dateTimeZone: DateTimeZone
) {

    val date: LocalDate
        get() = dateTime.toLocalDate()

    val dateString: String
        get() {
            val isoFormat = date.toString()

            @SuppressLint("SimpleDateFormat")
            val dateIsoParser = SimpleDateFormat("yyyy-MM-dd")
            val dateObj = dateIsoParser.parse(isoFormat)
            dateIsoParser.applyPattern("EEE MMM dd, yyyy")
            return dateIsoParser.format(dateObj)
        }

    val year: Int
        get() = date.year

    val month: Int
        get() = date.monthOfYear

    val day: Int
        get() = date.dayOfMonth

    val time: LocalTime
        get() = dateTime.toLocalTime()

    val timeString: String
        get() {
            val isoFormat = time.toString()

            @SuppressLint("SimpleDateFormat")
            val timeIsoFormat = SimpleDateFormat("HH:mm:ss.SSS")
            val timeObj = timeIsoFormat.parse(isoFormat)
            timeIsoFormat.applyPattern("HH:mm:ss")
            return timeIsoFormat.format(timeObj)
        }

    val hour: Int
        get() = time.hourOfDay

    val minute: Int
        get() = time.minuteOfHour

    // https://stackoverflow.com/a/21420765
    val utcOffset: Long
        get() {
            val instant = DateTime.now().millis
            val offsetInMillis = dateTimeZone.getOffset(instant).toLong()
            return TimeUnit.MILLISECONDS.toHours(offsetInMillis)
        }

    fun withTimeZone(dateTimeZone: DateTimeZone): Timestamp? {
        // TODO(jmmxp): Make return type non-nullable, finish function
        return null
    }
}