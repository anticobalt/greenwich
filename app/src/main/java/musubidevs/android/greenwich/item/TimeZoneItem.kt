package musubidevs.android.greenwich.item

import android.os.Parcelable
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.list_item_timezone.view.*
import musubidevs.android.greenwich.R

@Parcelize
class TimeZoneItem(val region: String, val utcOffset: String) :
    AbstractItem<TimeZoneItem.TimeZoneViewHolder>(), Parcelable {

    override val layoutRes: Int
        get() = R.layout.list_item_timezone
    override val type: Int
        get() = R.id.timeZoneView

    override fun getViewHolder(v: View): TimeZoneViewHolder {
        return TimeZoneViewHolder(v)
    }

    class TimeZoneViewHolder(itemView: View) : FastAdapter.ViewHolder<TimeZoneItem>(itemView) {

        override fun bindView(item: TimeZoneItem, payloads: MutableList<Any>) {
            itemView.region.text = item.region
            itemView.utcOffset.text = item.utcOffset
        }

        override fun unbindView(item: TimeZoneItem) {
            itemView.region.text = null
            itemView.utcOffset.text = null
        }

    }
}