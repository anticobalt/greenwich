package musubidevs.android.greenwich.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.listeners.ItemFilterListener
import kotlinx.android.synthetic.main.activity_time_zone.*
import kotlinx.android.synthetic.main.list_item_time_zone.*
import musubidevs.android.greenwich.R
import musubidevs.android.greenwich.item.TimeZoneItem
import musubidevs.android.greenwich.layout.TimeZoneCardMargin
import musubidevs.android.greenwich.model.UtcOffset
import musubidevs.android.greenwich.model.UtcOffset.Companion.minusSign
import musubidevs.android.greenwich.model.UtcOffset.Companion.plusSign
import org.joda.time.DateTimeZone

class TimeZoneActivity : CyaneaAppCompatActivity(), ItemFilterListener<TimeZoneItem> {

    private lateinit var fastItemAdapter: FastItemAdapter<TimeZoneItem>

    override fun onCreate(savedInstanceState: Bundle?) {

        val resId = getSharedPreferences(MainActivity.MAIN_PREFS, Context.MODE_PRIVATE)
            .getInt(MainActivity.THEME_PREFS_KEY, R.style.DayTheme)
        setTheme(resId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_zone)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        timeZoneRecycler.layoutManager = LinearLayoutManager(this)
        setDefaultTimeZoneViews()
        setRecyclerSpacing()
        setAdapter()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val search = (menu.findItem(R.id.search).actionView as SearchView)
        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fastItemAdapter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                fastItemAdapter.filter(newText)
                return true
            }
        })
        search.queryHint = getString(R.string.time_zone_search_hint)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    /**
     * Similar to [MainActivity.setRecyclerSpacing]: top of item has no margin,
     * but RecyclerView has padding with no clipping, to give illusion of space.
     */
    private fun setRecyclerSpacing() {
        val margin = resources.getDimensionPixelSize(R.dimen.card_margin)
        timeZoneRecycler.addItemDecoration(TimeZoneCardMargin(margin))
        timeZoneRecycler.setPadding(0, margin, 0, 0)
    }

    /**
     * Binds adapter and makes items filterable
     */
    @SuppressLint("DefaultLocale")
    private fun setAdapter() {
        fastItemAdapter = FastItemAdapter()
        timeZoneRecycler.adapter = fastItemAdapter
        fastItemAdapter.itemFilter.filterPredicate =
            { item: TimeZoneItem, constraint: CharSequence? ->
                // Filter regions that contain query or offsets whose hour
                val constraintLower = constraint.toString().toLowerCase()
                val region = item.region.toLowerCase()
                val offset = item.utcOffset.replaceFirst("[A-z]+".toRegex(), "")
                region.contains(constraintLower) || matchesOffset(constraintLower, offset)
            }

        fastItemAdapter.itemFilter.itemFilterListener = this

        val items = intent.getParcelableArrayListExtra<TimeZoneItem>(
            KEY_TIME_ZONE_ITEMS
        )
        fastItemAdapter.add(items)
    }

    /**
     * @param query Any string
     * @param offset String in form +13:45, -13, -3, etc
     */
    private fun matchesOffset(query: String, offset: String) : Boolean {
        var signedQuery = query.replace('-', minusSign)
        if (minusSign !in signedQuery && plusSign !in signedQuery) signedQuery = "$plusSign$query"

        return when {
            // If searching by sign, match all that have that sign
            (signedQuery == plusSign.toString() || signedQuery == minusSign.toString()) ->
                offset.startsWith(signedQuery)

            // If searching by minutes, match all that are NOT on the hour
            // and whose minutes component starts with query
            query.startsWith(":") ->
                ':' in offset && offset.split(":")[1].startsWith(query.removePrefix(":"))

            // If searching by hour, match hour exactly (e.g. query=1 matches +1:30, not +11:30)
            ':' !in signedQuery -> offset.split(":")[0].endsWith(signedQuery)

            // If ':' in middle of query, match lazily
            else -> offset.startsWith(signedQuery)
        }
    }

    override fun itemsFiltered(constraint: CharSequence?, results: List<TimeZoneItem>?) {
        // Implementation not required
    }

    override fun onReset() {
        // Implementation not required
    }

    private fun setDefaultTimeZoneViews() {
        val region = DateTimeZone.getDefault().id
        val offset = UtcOffset.default().toString()
        regionView.text = region
        offsetView.text = offset
        regionView.setTextColor(cyanea.accent)
        offsetView.setTextColor(cyanea.accent)
    }

    companion object {
        const val KEY_TIME_ZONE_ITEMS = "musubidevs.android.greenwich.time_zone_items"
    }
}
