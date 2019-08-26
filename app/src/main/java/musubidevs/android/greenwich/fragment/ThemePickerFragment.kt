package musubidevs.android.greenwich.fragment

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jaredrummler.cyanea.app.BaseCyaneaActivity
import kotlinx.android.synthetic.main.dialog_theme_picker.view.*
import musubidevs.android.greenwich.R

class ThemePickerFragment: DialogFragment() {

    private var primaryColor : Int? = null
    private var accentColor : Int? = null
    private lateinit var themePicker : View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context!!)
        themePicker = View.inflate(
            context,
            R.layout.dialog_theme_picker,
            null
        )
        val dialog = dialogBuilder
            .setView(themePicker)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> setTheme() }
            .create()

        buildThemePicker(dialog)

        return dialog
    }

    private fun setTheme() {
        val currentPrimary = ContextCompat.getColor(context!!, R.color.cyanea_primary_reference)
        val currentAccent = ContextCompat.getColor(context!!, R.color.cyanea_accent_reference)
        (activity as? BaseCyaneaActivity)?.cyanea?.edit {
            primary(primaryColor ?: currentPrimary)
            accent(accentColor ?: currentAccent)
        }?.recreate(activity!!)
    }

    private fun buildThemePicker(dialog: AlertDialog) {
        themePicker.primaryColors.setSelectedColor(ContextCompat.getColor(context!!, R.color.cyanea_primary_reference))
        themePicker.accentColors.setSelectedColor(ContextCompat.getColor(context!!, R.color.cyanea_accent_reference))

        themePicker.primaryColors.setOnColorSelectedListener {
            primaryColor = it
        }

        themePicker.accentColors.setOnColorSelectedListener {
            accentColor = it
            //dialog.getButton(AlertDialog.BUTTON_POSITIVE).backgroundTintList = ColorStateList.valueOf(it)
        }
    }

}