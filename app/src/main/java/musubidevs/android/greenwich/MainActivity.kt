package musubidevs.android.greenwich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import musubidevs.android.greenwich.layout.SingleColumnCardMargin
import musubidevs.android.greenwich.model.Conversion

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : AppCompatActivity() {

    private val conversions = mutableListOf<Conversion>()
    private lateinit var conversionAdapter: ConversionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conversionAdapter = ConversionAdapter(conversions, supportFragmentManager)
        conversionRecycler.layoutManager = LinearLayoutManager(this)
        conversionRecycler.adapter = conversionAdapter
        conversionRecycler.addItemDecoration(SingleColumnCardMargin(resources.getDimensionPixelSize(R.dimen.card_margin)))

        createConversionButton.setOnClickListener { addNewConversion() }
    }

    private fun addNewConversion() {
        conversions.add(0, Conversion())
        conversionAdapter.notifyItemInserted(0)
        conversionRecycler.smoothScrollToPosition(0)
    }
}
