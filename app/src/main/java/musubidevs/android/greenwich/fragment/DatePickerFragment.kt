package musubidevs.android.greenwich.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.model.SourceTimestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class DatePickerFragment(private val currentTimestamp: SourceTimestamp, private val onDateSetCallback: (SourceTimestamp) -> Unit) :
    DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            context!!,
            this,
            currentTimestamp.year,
            currentTimestamp.month - 1,
            currentTimestamp.day
        )
    }

    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        onDateSetCallback(currentTimestamp.withDate(year, month + 1, day))
    }

}