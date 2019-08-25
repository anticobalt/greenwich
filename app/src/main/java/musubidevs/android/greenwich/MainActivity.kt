package musubidevs.android.greenwich

import android.content.Context
import android.os.Bundle
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import musubidevs.android.greenwich.layout.SingleColumnCardMargin
import musubidevs.android.greenwich.model.Conversion

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : CyaneaAppCompatActivity() {

    private lateinit var conversions: MutableList<Conversion>
    private lateinit var conversionAdapter: ConversionAdapter
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

    override fun onResume() {
        super.onResume()
        conversions = readConversionsFromPrefs()
        conversionAdapter = ConversionAdapter(conversions, supportFragmentManager)
        conversionRecycler.adapter = conversionAdapter
    }

    override fun onPause() {
        super.onPause()
        saveConversionsToPrefs()
    }

    private fun addNewConversion() {
        conversions.add(0, Conversion())
        conversionAdapter.notifyItemInserted(0)
        conversionRecycler.smoothScrollToPosition(0)
    }

    private fun saveConversionsToPrefs() {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(CONVERSIONS_PREFS_KEY, gson.toJson(conversions))
        }
    }

    private fun readConversionsFromPrefs(): MutableList<Conversion> {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        val conversionsJson = prefs.getString(CONVERSIONS_PREFS_KEY, "") ?: return mutableListOf()
        if (conversionsJson.isEmpty()) return mutableListOf()
        return gson.fromJson(conversionsJson, object: TypeToken<MutableList<Conversion>>() {}.type)
    }

    companion object {
        private const val CONVERSIONS_PREFS_NAME = "conversion_prefs"
        private const val CONVERSIONS_PREFS_KEY = "conversions"
    }
}
