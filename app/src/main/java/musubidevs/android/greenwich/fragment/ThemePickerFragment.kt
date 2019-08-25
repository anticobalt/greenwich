package musubidevs.android.greenwich.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import musubidevs.android.greenwich.R

class ThemePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val themePicker = View.inflate(
            context,
            R.layout.dialog_theme_picker,
            null
        )
        return dialogBuilder
            .setView(themePicker)
            .setTitle("Choose Theme Colors")
            .setPositiveButton("OK") { _, _ -> }
            .create()
    }

}