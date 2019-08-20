package musubidevs.android.greenwich.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class TimeZonePickerFragment(private val currentTimestamp: Timestamp) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    private var onTimeZoneSetListener: OnTimeZoneSetInterface? = null

    interface OnTimeZoneSetInterface {
        fun onTimeSet(timestamp: Timestamp)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnTimeZoneSetInterface) {
            throw ClassCastException("The hosting activity does not implement OnTimeZoneSetInterface")
        }
        onTimeZoneSetListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(context, this, currentTimestamp.hour, currentTimestamp.minute, true)
    }

    override fun onTimeSet(picker: TimePicker?, hour: Int, minute: Int) {
//        onTimeZoneSetListener?.onTimeSet(currentTimestamp.withTimeZone(hour, minute))
    }

}