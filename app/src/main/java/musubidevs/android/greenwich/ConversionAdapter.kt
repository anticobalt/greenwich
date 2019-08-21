package musubidevs.android.greenwich

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_target_timestamp.view.*
import musubidevs.android.greenwich.fragment.TimeZonePickerFragment
import musubidevs.android.greenwich.model.Conversion
import musubidevs.android.greenwich.model.SourceTimestamp

/**
 * @author jmmxp
 */
class ConversionAdapter(
    private val conversions: MutableList<Conversion>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ConversionAdapter.ConversionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        return ConversionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_target_timestamp,
                parent,
                false
            ),
            fragmentManager
        )
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bind(conversions[position])
    }

    override fun getItemCount(): Int {
        return conversions.size
    }

    fun onSourceTimestampUpdate(sourceTimestamp: SourceTimestamp) {
        for (conversion in conversions) {
            targetTimestamp.withSource(sourceTimestamp)
        }
    }

    class ConversionViewHolder(itemView: View, fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var targetTimestamp: Conversion
        private var dateView: TextView = itemView.sourceDateView
        private var timeView: TextView = itemView.sourceTimeView
        private var timezoneView: TextView = itemView.sourceTimeZoneView

        init {
            timezoneView.setOnClickListener {
                TimeZonePickerFragment(targetTimestamp).show(fragmentManager, "timeZonePicker")
            }
        }

        fun bind(conversion: Conversion) {
            targetTimestamp = conversion
            dateView.text = timestamp.dateString
            timeView.text = timestamp.timeString
            timezoneView.text = itemView.context.getString(R.string.utc, timestamp.utcOffset)
        }
    }
}

