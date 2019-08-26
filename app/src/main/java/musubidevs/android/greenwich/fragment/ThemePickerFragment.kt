package musubidevs.android.greenwich.fragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_theme_picker.view.*
import musubidevs.android.greenwich.MainActivity
import musubidevs.android.greenwich.MainActivity.Companion.ACCENT_COLOR
import musubidevs.android.greenwich.MainActivity.Companion.MAIN_PREFS
import musubidevs.android.greenwich.MainActivity.Companion.PRIMARY_COLOR
import musubidevs.android.greenwich.R
import kotlin.math.max


class ThemePickerFragment : DialogFragment() {

    private var primaryColor : Int? = null
    private var accentColor : Int? = null
    private lateinit var themePicker: View
    private var prefs : SharedPreferences? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context!!)
        themePicker = View.inflate(
            context, R.layout.dialog_theme_picker,
            null
        )
        val dialog = dialogBuilder
            .setView(themePicker)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> saveTheme() }
            .create()

        prefs = activity?.getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE)
        if (prefs != null) {
            getCurrentColors()
            buildThemePicker(dialog)
        }

        return dialog
    }

    private fun getCurrentColors()  {
        primaryColor = prefs?.getInt(PRIMARY_COLOR, ContextCompat.getColor(context!!, R.color.colorPrimary))
        accentColor = prefs?.getInt(ACCENT_COLOR, ContextCompat.getColor(context!!, R.color.colorAccent))
    }

    private fun buildThemePicker(dialog: AlertDialog) {
        val mainActivity = activity as MainActivity
        themePicker.primaryColors.setSelectedColor(primaryColor!!)
        themePicker.primaryColors.setOnColorSelectedListener {
            primaryColor = it
            Toast.makeText(context, String.format("#%06X", 0xFFFFFF and it), Toast.LENGTH_SHORT)
                .show()
            for (callback in mainActivity.colorWithPrimaryCallbacks) {
                callback(it)
            }
            mainActivity.window.statusBarColor = darker(it)
        }

        themePicker.accentColors.setSelectedColor(accentColor!!)
        themePicker.accentColors.setOnColorSelectedListener {
            accentColor = it
            Toast.makeText(context, String.format("#%06X", 0xFFFFFF and it), Toast.LENGTH_SHORT)
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(it)
            for (callback in mainActivity.colorWithAccentCallbacks) {
                callback(it)
            }
        }
    }

    private fun saveTheme() {
        prefs?.edit {
            putInt(PRIMARY_COLOR, primaryColor!!)
            putInt(ACCENT_COLOR, accentColor!!)
        }
    }


    /**
     * From ColorUtils class in https://github.com/jaredrummler/Cyanea.
     * Has to be the exact same formula as used by Cyanea, so that the preview color
     * and the final set color do not vary.
     */
    private fun darker(color: Int, factor: Float = 0.85f): Int {
        return Color.argb(
            Color.alpha(color), max((Color.red(color) * factor).toInt(), 0),
            max((Color.green(color) * factor).toInt(), 0),
            max((Color.blue(color) * factor).toInt(), 0)
        )
    }

}