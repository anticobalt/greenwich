package musubidevs.android.greenwich.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.model.Timestamp
import musubidevs.android.greenwich.model.UtcOffset
import org.joda.time.DateTimeZone

/**
 * @author anticobalt
 * @author jmmxp
 */
class TimeZonePickerFragment(
    private val currentTimestamp: Timestamp,
    private val onTimeZoneSetCallback: (Timestamp) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val numberPicker = View.inflate(
            context,
            R.layout.dialog_time_zone_picker,
            null
        ) as NumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = UTC_OFFSETS.size - 1
        val utcOffsetStrings = UTC_OFFSETS.map { offset -> offset.utcString }
        numberPicker.displayedValues = utcOffsetStrings.toTypedArray()

        return dialogBuilder
            .setView(numberPicker)
            .setTitle("What time zone are you in?")
            .setPositiveButton("OK") { _, _ ->
                onTimeZoneSet(numberPicker.value)
            }.create()
    }

    private fun onTimeZoneSet(utcOffsetIndex: Int) {
        val offset = UTC_OFFSETS[utcOffsetIndex]
        val dateTimeZone =
            DateTimeZone.forOffsetHoursMinutes(offset.utcOffsetHours, offset.utcOffsetMinutes)
        onTimeZoneSetCallback(currentTimestamp.withTimeZone(dateTimeZone))
    }

    companion object {
        val UTC_OFFSETS = listOf(
            UtcOffset("-12:00", -12, 0),
            UtcOffset("-11:00", -11, 0),
            UtcOffset("-10:00", -10, 0),
            UtcOffset("-09:30", -9, 30),
            UtcOffset("-09:00", -9, 0),
            UtcOffset("-08:00", -8, 0),
            UtcOffset("-07:00", -7, 0),
            UtcOffset("-06:00", -6, 0),
            UtcOffset("-05:00", -5, 0),
            UtcOffset("-04:00", -4, 0),
            UtcOffset("-03:30", -3, 30),
            UtcOffset("-03:00", -3, 0),
            UtcOffset("-02:00", -2, 0),
            UtcOffset("-01:00", -1, 0),
            UtcOffset("±00:00", 0, 0),
            UtcOffset("+01:00", 1, 0),
            UtcOffset("+02:00", 2, 0),
            UtcOffset("+03:00", 3, 0),
            UtcOffset("+03:30", 3, 30),
            UtcOffset("+04:00", 4, 0),
            UtcOffset("+04:30", 4, 30),
            UtcOffset("+05:00", 5, 0),
            UtcOffset("+05:30", 5, 30),
            UtcOffset("+05:45", 5, 45),
            UtcOffset("+06:00", 6, 0),
            UtcOffset("+06:30", 6, 30),
            UtcOffset("+07:00", 7, 0),
            UtcOffset("+08:00", 8, 0),
            UtcOffset("+08:45", 8, 45),
            UtcOffset("+09:00", 9, 0),
            UtcOffset("+09:30", 9, 30),
            UtcOffset("+10:00", 10, 0),
            UtcOffset("+10:30", 10, 30),
            UtcOffset("+11:00", 11, 0),
            UtcOffset("+12:00", 12, 0),
            UtcOffset("+12:45", 12, 45),
            UtcOffset("+13:00", 13, 0),
            UtcOffset("+14:00", 14, 0)
        )
    }

}