package musubidevs.android.greenwich

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.component_conversion_source.view.*
import kotlinx.android.synthetic.main.list_item_conversion.view.*
import musubidevs.android.greenwich.fragment.DatePickerFragment
import musubidevs.android.greenwich.fragment.TimePickerFragment
import musubidevs.android.greenwich.fragment.TimeZonePickerFragment
import musubidevs.android.greenwich.model.Conversion
import musubidevs.android.greenwich.model.SourceTimestamp
import musubidevs.android.greenwich.model.TargetTimestamp
import musubidevs.android.greenwich.model.Timestamp

/**
 * @author jmmxp
 * @author anticobalt
 */
class ConversionAdapter(
    private val conversions: MutableList<Conversion>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ConversionAdapter.ConversionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        return ConversionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_conversion,
                parent,
                false
            ),
            fragmentManager,
            this
        )
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bind(conversions[position])
    }

    override fun getItemCount(): Int {
        return conversions.size
    }

    class ConversionViewHolder(
        itemView: View,
        private val fragmentManager: FragmentManager,
        private val adapter: ConversionAdapter
    ) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var conversion: Conversion
        private lateinit var sourceTimestamp: SourceTimestamp
        private lateinit var targetTimestamp: TargetTimestamp
        private var sourceDateView: TextView = itemView.sourceDateView.textView as TextView
        private var sourceTimeView: TextView = itemView.sourceTimeView.textView as TextView
        private var sourceTimeZoneView: TextView = itemView.sourceTimeZoneView.textView as TextView
        private var targetDateView: TextView = itemView.targetDateView as TextView
        private var targetTimeView: TextView = itemView.targetTimeView as TextView
        private var targetTimeZoneView: TextView = itemView.targetTimeZoneView as TextView

        init {
            setSourceOnClicks()
        }

        fun bind(conversion: Conversion) {
            this.conversion = conversion
            this.sourceTimestamp = conversion.sourceTimestamp
            this.targetTimestamp = conversion.targetTimestamp

            sourceDateView.text = sourceTimestamp.dateString
            sourceTimeView.text = sourceTimestamp.timeString
            sourceTimeZoneView.text =
                itemView.context.getString(R.string.utc, sourceTimestamp.utcOffset)

            targetDateView.text = targetTimestamp.dateString
            targetTimeView.text = targetTimestamp.timeString
            targetTimeZoneView.text =
                itemView.context.getString(R.string.utc, targetTimestamp.utcOffset)
        }

        private fun setSourceOnClicks() {
            sourceDateView.setOnClickListener {
                DatePickerFragment(sourceTimestamp) { update(it) }.show(
                    fragmentManager,
                    "datePicker"
                )
            }
            sourceTimeView.setOnClickListener {
                TimePickerFragment(sourceTimestamp) { update(it) }.show(
                    fragmentManager,
                    "timePicker"
                )
            }
            sourceTimeZoneView.setOnClickListener {
                TimeZonePickerFragment(sourceTimestamp) { update(it) }.show(
                    fragmentManager,
                    "timeZonePicker"
                )
            }

            targetTimeZoneView.setOnClickListener {
                TimeZonePickerFragment(targetTimestamp) { update(it) }.show(
                    fragmentManager,
                    "timeZonePicker"
                )
            }
        }

        private fun update(newTimestamp: Timestamp) {
            conversion.updateTimestamp(newTimestamp)
            adapter.notifyItemChanged(adapterPosition)
        }

    }
}

