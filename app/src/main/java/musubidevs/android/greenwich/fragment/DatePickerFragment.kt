package musubidevs.android.greenwich.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class DatePickerFragment(private val currentTimestamp: Timestamp) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private var onDateSetListener: OnDateSetInterface? = null

    interface OnDateSetInterface {
        fun onDateSet(timestamp: Timestamp)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnDateSetInterface) {
            throw ClassCastException("The hosting activity does not implement OnTimeSetInterface")
        }
        onDateSetListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(context!!, this, currentTimestamp.year, currentTimestamp.month, currentTimestamp.day)
    }

    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        val copy = currentTimestamp.copy()
        copy.updateDate(year, month, day)
        onDateSetListener?.onDateSet(copy)
    }

}