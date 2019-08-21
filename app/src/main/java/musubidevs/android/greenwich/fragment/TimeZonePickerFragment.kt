package musubidevs.android.greenwich.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.model.Timestamp
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
        val numberPicker = LayoutInflater.from(context).inflate(
            R.layout.dialog_time_zone_picker,
            null
        ) as NumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = +14

        return dialogBuilder.setView(numberPicker).setPositiveButton("OK") { _, _ ->
            onTimeZoneSet(numberPicker.value)
        }.create()
    }

    private fun onTimeZoneSet(utcOffset: Int) {
        val dateTimeZone = DateTimeZone.forOffsetHours(utcOffset)
        onTimeZoneSetCallback(currentTimestamp.withTimeZone(dateTimeZone))
    }
}