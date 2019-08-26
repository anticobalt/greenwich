package musubidevs.android.greenwich.model

import android.annotation.SuppressLint
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import java.text.SimpleDateFormat

/**
 * Represents an instant in time, with a timezone, date, and time.
 *
 * @author anticobalt
 * @author jmmxp
 */
abstract class Timestamp(
    internal val dateTime: DateTime,
    internal val utcOffset: UtcOffset
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
            timeIsoFormat.applyPattern("h:mm a")
            return timeIsoFormat.format(timeObj)
        }

    val hour: Int
        get() = time.hourOfDay

    val minute: Int
        get() = time.minuteOfHour

    val utcOffsetMillis: Long
        get() = utcOffset.toMillis()
    
    val utcOffsetString: String
        get() = utcOffset.toString()

    abstract fun withOffset(utcOffset: UtcOffset): Timestamp
}