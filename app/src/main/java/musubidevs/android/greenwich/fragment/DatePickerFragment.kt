package musubidevs.android.greenwich.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.model.SourceTimestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class DatePickerFragment(private val currentTimestamp: SourceTimestamp) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private var onDateSetListener: OnDateSetInterface? = null

    interface OnDateSetInterface {
        fun onDateSet(timestamp: SourceTimestamp)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnDateSetInterface) {
            throw ClassCastException("The hosting activity does not implement OnDateSetInterface")
        }
        onDateSetListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(context!!, this, currentTimestamp.year, currentTimestamp.month - 1, currentTimestamp.day)
    }

    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        onDateSetListener?.onDateSet(currentTimestamp.withDate(year, month + 1, day))
    }

}