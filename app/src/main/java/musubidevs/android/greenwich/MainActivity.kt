package musubidevs.android.greenwich

import android.content.Context
import android.os.Bundle
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
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
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conversions = mutableListOf()
        conversionAdapter = ConversionAdapter(conversions, supportFragmentManager)
        conversionRecycler.layoutManager = LinearLayoutManager(this)
        conversionRecycler.adapter = conversionAdapter
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
        readConversionsFromPrefs()
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

    private inline fun <reified T> genericType() = object: TypeToken<T>() {}.type!!

    private fun readConversionsFromPrefs() {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        // TODO(jmmxp): Ensure empty string works for JSON parsing
        val conversionsJson = prefs.getString(CONVERSIONS_PREFS_NAME, "") ?: return
        if (conversionsJson.isEmpty()) return
        conversions = gson.fromJson(conversionsJson, genericType<MutableList<Conversion>>())
    }

    companion object {
        private const val CONVERSIONS_PREFS_NAME = "conversions"
        private const val CONVERSIONS_PREFS_KEY = "conversions"
    }
}
