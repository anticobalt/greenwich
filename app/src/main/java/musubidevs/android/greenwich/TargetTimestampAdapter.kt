package musubidevs.android.greenwich

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_target_timestamp.view.*
import musubidevs.android.greenwich.fragment.TimeZonePickerFragment
import musubidevs.android.greenwich.model.SourceTimestamp
import musubidevs.android.greenwich.model.TargetTimestamp

/**
 * @author jmmxp
 */
class TargetTimestampAdapter(
    private val targetTimestamps: MutableList<TargetTimestamp>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<TargetTimestampAdapter.TargetTimestampViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetTimestampViewHolder {
        return TargetTimestampViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_target_timestamp,
                parent,
                false
            ),
            fragmentManager
        )
    }

    override fun onBindViewHolder(holder: TargetTimestampViewHolder, position: Int) {
        holder.bind(targetTimestamps[position])
    }

    override fun getItemCount(): Int {
        return targetTimestamps.size
    }

    fun onSourceTimestampUpdate(sourceTimestamp: SourceTimestamp) {
        for (targetTimestamp in targetTimestamps) {
            targetTimestamp.withSource(sourceTimestamp)
        }
    }

    class TargetTimestampViewHolder(itemView: View, fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var targetTimestamp: TargetTimestamp
        private var dateView: TextView = itemView.dateView
        private var timeView: TextView = itemView.timeView
        private var timezoneView: TextView = itemView.timezoneView

        init {
            timezoneView.setOnClickListener {
                TimeZonePickerFragment(targetTimestamp).show(fragmentManager, "timeZonePicker")
            }
        }

        fun bind(timestamp: TargetTimestamp) {
            targetTimestamp = timestamp
            dateView.text = timestamp.dateString
            timeView.text = timestamp.timeString
            timezoneView.text = itemView.context.getString(R.string.utc, timestamp.utcOffset)
        }
    }
}

