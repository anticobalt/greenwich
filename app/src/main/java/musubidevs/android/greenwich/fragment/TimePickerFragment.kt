package musubidevs.android.greenwich.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.model.SourceTimestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class TimePickerFragment(
    private val currentTimestamp: SourceTimestamp,
    private val onTimeSetCallback: (SourceTimestamp) -> Unit
) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(context, this, currentTimestamp.hour, currentTimestamp.minute, false)
    }

    override fun onTimeSet(picker: TimePicker?, hour: Int, minute: Int) {
        onTimeSetCallback(currentTimestamp.withTime(hour, minute))
    }

}