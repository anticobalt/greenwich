package musubidevs.android.greenwich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_target_timestamp.view.*
import musubidevs.android.greenwich.fragment.DatePickerFragment
import musubidevs.android.greenwich.fragment.TimePickerFragment
import musubidevs.android.greenwich.layout.SingleColumnCardMargin
import musubidevs.android.greenwich.model.Conversion
import musubidevs.android.greenwich.model.SourceTimestamp
import musubidevs.android.greenwich.model.TargetTimestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : AppCompatActivity(), TimePickerFragment.OnTimeSetInterface, DatePickerFragment.OnDateSetInterface {

    private val conversions = mutableListOf<Conversion>()
    private lateinit var conversionAdapter: ConversionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillSourceView()
        setSourceOnClicks()

        conversionAdapter = ConversionAdapter(conversions, supportFragmentManager)
        timestampRecycler.layoutManager = LinearLayoutManager(this)
        timestampRecycler.adapter = conversionAdapter
        timestampRecycler.addItemDecoration(SingleColumnCardMargin(resources.getDimensionPixelSize(R.dimen.card_margin)))

        createTargetTimestampButton.setOnClickListener { addTargetTimestamp() }
    }

    private fun addTargetTimestamp() {
        // TODO(jmmxp): Change this target timestamp from the default one
        conversions.add(Conversion())
        conversionAdapter.notifyDataSetChanged()
    }

    private fun fillSourceView() {
        sourceView.dateView.text = sourceTimestamp.dateString
        sourceView.timeView.text = sourceTimestamp.timeString
        sourceView.timezoneView.text = getString(R.string.utc, sourceTimestamp.utcOffset)
    }

    private fun setSourceOnClicks() {
        sourceView.dateView.setOnClickListener { showDatePicker() }
        sourceView.timeView.setOnClickListener { showTimePicker() }
    }

    private fun showDatePicker() {
        DatePickerFragment(sourceTimestamp).show(supportFragmentManager, "datePicker")
    }

    private fun showTimePicker() {
        TimePickerFragment(sourceTimestamp).show(supportFragmentManager, "timePicker")
    }

    override fun onTimeSet(timestamp: SourceTimestamp) {
        sourceTimestamp = timestamp
        fillSourceView()
        conversionAdapter.onSourceTimestampUpdate(timestamp)
    }

    override fun onDateSet(timestamp: SourceTimestamp) {
        sourceTimestamp = timestamp
        fillSourceView()
        conversionAdapter.onSourceTimestampUpdate(timestamp)
    }
}
