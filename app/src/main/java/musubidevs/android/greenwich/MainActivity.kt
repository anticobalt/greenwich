package musubidevs.android.greenwich

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import musubidevs.android.greenwich.fragment.ThemePickerFragment
import musubidevs.android.greenwich.layout.SingleColumnCardMargin
import musubidevs.android.greenwich.model.Conversion

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : CyaneaAppCompatActivity() {

    private lateinit var conversions: MutableList<ConversionItem>
    private lateinit var conversionItemAdapter: ItemAdapter<ConversionItem>
    private val gson = Converters.registerDateTime(GsonBuilder()).create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conversionRecycler.layoutManager = LinearLayoutManager(this)
        conversionRecycler.addItemDecoration(
            SingleColumnCardMargin(
                resources.getDimensionPixelSize(
                    R.dimen.card_margin
                )
            )
        )

        createConversionButton.setOnClickListener {
            addNewConversion()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.theme -> openThemePicker()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun openThemePicker() {
        ThemePickerFragment().show(supportFragmentManager, "themePicker")
    }

    override fun onResume() {
        super.onResume()
        conversionItemAdapter = ItemAdapter()
        conversions = readConversionsFromPrefs()
        conversionItemAdapter.add(conversions)
        val fastAdapter = FastAdapter.with(conversionItemAdapter)
        conversionRecycler.adapter = fastAdapter
    }

    override fun onPause() {
        super.onPause()
        saveConversionsToPrefs()
    }

    private fun addNewConversion() {
        @Suppress("UNCHECKED_CAST")
        conversionItemAdapter.add(0, ConversionItem(Conversion(), supportFragmentManager,
            conversionRecycler.adapter as FastAdapter<ConversionItem>
        ))
        conversionRecycler.smoothScrollToPosition(0)
    }

    private fun saveConversionsToPrefs() {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(CONVERSIONS_PREFS_KEY, gson.toJson(conversions))
        }
    }

    private fun readConversionsFromPrefs(): MutableList<ConversionItem> {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        val conversionsJson =
            prefs.getString(CONVERSIONS_PREFS_KEY, "") ?: return mutableListOf()
        if (conversionsJson.isEmpty()) return mutableListOf()
        return gson.fromJson(
            conversionsJson,
            object : TypeToken<MutableList<ConversionItem>>() {}.type
        )
    }

    companion object {
        private const val CONVERSIONS_PREFS_NAME = "conversion_prefs"
        private const val CONVERSIONS_PREFS_KEY = "conversions"
    }
}
