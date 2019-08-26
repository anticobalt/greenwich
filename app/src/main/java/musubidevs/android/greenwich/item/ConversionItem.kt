package musubidevs.android.greenwich.item

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.swipe.ISwipeable
import kotlinx.android.synthetic.main.list_item_conversion.view.*
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.fragment.DatePickerFragment
import musubidevs.android.greenwich.fragment.TimePickerFragment
import musubidevs.android.greenwich.fragment.TimeZonePickerFragment
import musubidevs.android.greenwich.model.Conversion
import musubidevs.android.greenwich.model.SourceTimestamp
import musubidevs.android.greenwich.model.TargetTimestamp
import musubidevs.android.greenwich.model.Timestamp

/**
 * Represents a Conversion object. Has pickers. Is draggable and can be deleted via swipe gesture.
 *
 * @author jmmxp
 * @author anticobalt
 */
class ConversionItem(
    val conversion: Conversion,
    override val isSwipeable: Boolean,
    private val fragmentManager: FragmentManager,
    private val adapter: FastAdapter<ConversionItem>
) : ISwipeable, AbstractItem<ConversionItem.ConversionViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.list_item_conversion

    override val type: Int
        get() = R.id.conversionView

    override fun getViewHolder(v: View): ConversionViewHolder {
        return ConversionViewHolder(
            v,
            fragmentManager,
            adapter
        )
    }

    class ConversionViewHolder(
        itemView: View,
        private val fragmentManager: FragmentManager,
        private val adapter: FastAdapter<ConversionItem>
    ) :
        FastAdapter.ViewHolder<ConversionItem>(itemView) {

        private lateinit var conversion: Conversion
        private lateinit var sourceTimestamp: SourceTimestamp
        private lateinit var targetTimestamp: TargetTimestamp
        private val sourceDateView: TextView = itemView.sourceDateView as TextView
        private val sourceTimeView: TextView = itemView.sourceTimeView as TextView
        private val sourceTimeZoneView: TextView = itemView.sourceTimeZoneView as TextView
        private val targetDateView: TextView = itemView.targetDateView as TextView
        private val targetTimeView: TextView = itemView.targetTimeView as TextView
        private val targetTimeZoneView: TextView = itemView.targetTimeZoneView as TextView

        init {
            setSourceOnClicks()
            setTargetOnClicks()
        }

        fun hideView() {
            itemView.visibility = View.GONE
        }

        override fun bindView(item: ConversionItem, payloads: MutableList<Any>) {
            // ensure always visible after creating or undoing removal
            itemView.visibility = View.VISIBLE

            this.conversion = item.conversion
            this.sourceTimestamp = conversion.sourceTimestamp
            this.targetTimestamp = conversion.targetTimestamp

            sourceDateView.text = sourceTimestamp.dateString
            sourceTimeView.text = sourceTimestamp.timeString
            sourceTimeZoneView.text = sourceTimestamp.utcOffsetString

            targetDateView.text = targetTimestamp.dateString
            targetTimeView.text = targetTimestamp.timeString
            targetTimeZoneView.text = targetTimestamp.utcOffsetString
        }

        override fun unbindView(item: ConversionItem) {
            sourceDateView.text = null
            sourceTimeView.text = null
            sourceTimeZoneView.text = null
            targetDateView.text = null
            targetTimeView.text = null
            targetTimeZoneView.text = null
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
        }

        private fun setTargetOnClicks() {
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

