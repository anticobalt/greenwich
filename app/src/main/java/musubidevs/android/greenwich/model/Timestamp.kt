package musubidevs.android.greenwich.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import java.util.concurrent.TimeUnit

/**
 * @author anticobalt
 * @author jmmxp
 */
@Parcelize
data class Timestamp(
    private val dateTime: DateTime = DateTime.now(),
    private val dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
) : Parcelable {

    val date: LocalDate
        get() = dateTime.toLocalDate()

    val dateString: String
        get() = date.toString()

    val year: Int
        get() = date.year

    val month: Int
        get() = date.monthOfYear

    val day: Int
        get() = date.dayOfMonth

    val time: LocalTime
        get() = dateTime.toLocalTime()

    val timeString: String
        get() = time.toString()

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

    fun updateDate(year: Int, month: Int, day: Int) {}

    fun updateTime(hour: Int, minute: Int) {

    }

}