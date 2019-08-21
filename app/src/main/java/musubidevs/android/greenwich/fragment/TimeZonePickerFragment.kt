package musubidevs.android.greenwich.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class TimeZonePickerFragment(private val currentTimestamp: Timestamp) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    private var onTimeZoneSetListener: OnTimeZoneSetInterface? = null

    interface OnTimeZoneSetInterface {
        fun onTimeZoneSet(timestamp: Timestamp)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnTimeZoneSetInterface) {
            throw ClassCastException("The hosting activity does not implement OnTimeZoneSetInterface")
        }
        onTimeZoneSetListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context)
        return dialogBuilder.setView(R.layout.dialog_time_zone_picker).create()
    }

    override fun onTimeSet(picker: TimePicker?, hour: Int, minute: Int) {
//        onTimeZoneSetListener?.onTimeSet(currentTimestamp.withTimeZone(hour, minute))
    }

}