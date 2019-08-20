package musubidevs.android.greenwich

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_conversion.view.*
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author jmmxp
 */
class TimestampAdapter : RecyclerView.Adapter<TimestampAdapter.TimestampViewHolder>() {

    private val timestamps = mutableListOf<Timestamp>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimestampViewHolder {
        return TimestampViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_conversion,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimestampViewHolder, position: Int) {
        holder.bind(timestamps[position])
    }

    override fun getItemCount(): Int {
        return timestamps.size
    }

    fun addTimestamp(timestamp: Timestamp) {
        timestamps.add(timestamp)
        notifyDataSetChanged()
    }

    class TimestampViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dateView = itemView.dateView
        private var timeView = itemView.timeView
        private var timezoneView = itemView.timezoneView

        fun bind(timestamp: Timestamp) {
            dateView.text = timestamp.dateString
            timeView.text = timestamp.timeString
            timezoneView.text = itemView.context.getString(R.string.utc, timestamp.utcOffset)
        }
    }
}

