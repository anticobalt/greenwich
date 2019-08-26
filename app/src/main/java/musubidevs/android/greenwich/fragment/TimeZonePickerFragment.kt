package musubidevs.android.greenwich.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.model.SourceTimestamp
import musubidevs.android.greenwich.model.Timestamp
import musubidevs.android.greenwich.model.UtcOffset

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
        val utcOffsetStrings = UTC_OFFSETS.map { offset -> offset.toLongString() }
        numberPicker.displayedValues = utcOffsetStrings.toTypedArray()
        numberPicker.value = getCurrentOffsetIndex()

        val title = if (currentTimestamp is SourceTimestamp) getString(R.string.time_zone_from)
        else getString(R.string.time_zone_to)
        return dialogBuilder
            .setView(numberPicker)
            .setTitle(title)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                onTimeZoneSet(numberPicker.value)
            }.create()
    }

    private fun getCurrentOffsetIndex(): Int {
        for (i in UTC_OFFSETS.indices) {
            val utcOffset = UTC_OFFSETS[i]
            if (currentTimestamp.utcOffset == utcOffset) {
                return i
            }
        }

        return 0
    }

    private fun onTimeZoneSet(utcOffsetIndex: Int) {
        onTimeZoneSetCallback(currentTimestamp.withOffset(UTC_OFFSETS[utcOffsetIndex]))
    }

    companion object {
        val UTC_OFFSETS = listOf(
            UtcOffset(-12, 0),
            UtcOffset(-11, 0),
            UtcOffset( -10, 0),
            UtcOffset(-9, 30),
            UtcOffset(-9, 0),
            UtcOffset(-8, 0),
            UtcOffset(-7, 0),
            UtcOffset(-6, 0),
            UtcOffset(-5, 0),
            UtcOffset(-4, 0),
            UtcOffset(-3, 30),
            UtcOffset(-3, 0),
            UtcOffset(-2, 0),
            UtcOffset(-1, 0),
            UtcOffset(0, 0),
            UtcOffset(1, 0),
            UtcOffset(2, 0),
            UtcOffset(3, 0),
            UtcOffset(3, 30),
            UtcOffset(4, 0),
            UtcOffset(4, 30),
            UtcOffset(5, 0),
            UtcOffset(5, 30),
            UtcOffset(5, 45),
            UtcOffset(6, 0),
            UtcOffset(6, 30),
            UtcOffset(7, 0),
            UtcOffset(8, 0),
            UtcOffset(8, 45),
            UtcOffset(9, 0),
            UtcOffset(9, 30),
            UtcOffset(10, 0),
            UtcOffset(10, 30),
            UtcOffset(11, 0),
            UtcOffset(12, 0),
            UtcOffset(12, 45),
            UtcOffset(13, 0),
            UtcOffset(14, 0)
        )
    }

}