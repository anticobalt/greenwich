package musubidevs.android.greenwich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_conversion.view.*
import musubidevs.android.greenwich.fragment.DatePickerFragment
import musubidevs.android.greenwich.fragment.TimePickerFragment
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : AppCompatActivity(), TimePickerFragment.OnTimeSetInterface, DatePickerFragment.OnDateSetInterface {

    private val adapter: TimestampAdapter = TimestampAdapter()
    private lateinit var sourceTimestamp: Timestamp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceTimestamp = Timestamp()
        buildSourceView()
        setSourceOnClicks()

        timestampRecycler.layoutManager = LinearLayoutManager(this)
        timestampRecycler.adapter = adapter
        createConversionTimestampButton.setOnClickListener { adapter.addTimestamp(Timestamp()) }
    }

    private fun buildSourceView() {
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

    override fun onTimeSet(timestamp: Timestamp) {
        sourceTimestamp = timestamp
        buildSourceView()
    }

    override fun onDateSet(timestamp: Timestamp) {
        sourceTimestamp = timestamp
        buildSourceView()
    }

}
