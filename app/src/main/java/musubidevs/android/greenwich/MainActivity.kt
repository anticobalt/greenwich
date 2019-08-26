package musubidevs.android.greenwich

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.edit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.drag.ItemTouchCallback
import com.mikepenz.fastadapter.drag.SimpleDragCallback
import com.mikepenz.fastadapter.helpers.UndoHelper
import com.mikepenz.fastadapter.swipe.SimpleSwipeCallback
import kotlinx.android.synthetic.main.activity_main.*
import musubidevs.android.greenwich.fragment.ThemePickerFragment
import musubidevs.android.greenwich.layout.SingleColumnCardMargin
import musubidevs.android.greenwich.model.Conversion
import java.util.*

/**
 * @author anticobalt
 * @author jmmxp
 */
class MainActivity : CyaneaAppCompatActivity(), ItemTouchCallback, SimpleSwipeCallback.ItemSwipeCallback {
    private lateinit var itemAdapter: ItemAdapter<ConversionItem>
    private lateinit var fastAdapter: FastAdapter<ConversionItem>
    private lateinit var undoHelper: UndoHelper<ConversionItem>
    private val gson = Converters.registerDateTime(GsonBuilder()).create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conversionRecycler.layoutManager = LinearLayoutManager(this)
        conversionRecycler.addItemDecoration(
            SingleColumnCardMargin(
                resources.getDimensionPixelSize(
                    R.dimen.card_margin
                )
            )
        )

//        conversionRecycler.itemAnimator = DefaultItemAnimator()

        createConversionButton.setOnClickListener {
            addNewConversion()
        }

        val swipeCallback = ItemTouchHelper(SimpleSwipeCallback(this, null, bgColorLeft = Color.TRANSPARENT).withLeaveBehindSwipeLeft(getDrawable(R.drawable.ic_check_white_24dp)!!))
        val dragCallback = ItemTouchHelper(SimpleDragCallback())
        swipeCallback.attachToRecyclerView(conversionRecycler)
        dragCallback.attachToRecyclerView(conversionRecycler)

        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)
        conversionRecycler.adapter = fastAdapter

        undoHelper = UndoHelper(fastAdapter, object: UndoHelper.UndoListener<ConversionItem> {
            override fun commitRemove(
                positions: Set<Int>,
                removed: ArrayList<FastAdapter.RelativeInfo<ConversionItem>>
            ) {
                Log.e("UndoHelper", "Positions: " + positions.toString() + " Removed: " + removed.size)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.theme -> ThemePickerFragment().show(supportFragmentManager, "themePicker")
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        itemAdapter.add(readConversionsFromPrefs().map { ConversionItem(it, supportFragmentManager, fastAdapter) })
    }

    override fun onPause() {
        super.onPause()
        saveConversionsToPrefs()
    }

    private fun addNewConversion() {
        itemAdapter.add(0, ConversionItem(Conversion(), supportFragmentManager,
            fastAdapter
        ))
        conversionRecycler.smoothScrollToPosition(0)
    }

    private fun saveConversionsToPrefs() {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(CONVERSIONS_PREFS_KEY, gson.toJson(itemAdapter.adapterItems.map { it.conversion }) )
        }
    }

    private fun readConversionsFromPrefs(): MutableList<Conversion> {
        val prefs = getSharedPreferences(CONVERSIONS_PREFS_NAME, Context.MODE_PRIVATE)
        val conversionsJson =
            prefs.getString(CONVERSIONS_PREFS_KEY, "") ?: return mutableListOf()
        if (conversionsJson.isEmpty()) return mutableListOf()
        return gson.fromJson(
            conversionsJson,
            object : TypeToken<MutableList<Conversion>>() {}.type
        )
    }

    override fun itemTouchOnMove(oldPosition: Int, newPosition: Int): Boolean {
        // No need to implement
        return false
    }

    override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
        val items = itemAdapter.adapterItems
        val temp = items[oldPosition]
        items[oldPosition] = items[newPosition]
        items[newPosition] = temp
    }

    override fun itemSwiped(position: Int, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            undoHelper.remove(mainConstraintLayout, "Item removed", "Undo", Snackbar.LENGTH_LONG, setOf(position))
        }
    }

    companion object {
        private const val CONVERSIONS_PREFS_NAME = "conversion_prefs"
        private const val CONVERSIONS_PREFS_KEY = "conversions"
    }
}
