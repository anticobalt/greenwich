package musubidevs.android.greenwich.layout

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Used to add uniform margin. Assumes one column.
 *
 * @author anticobalt
 */
class SingleColumnCardMargin(private val space : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.top = space
        outRect.bottom = 0
    }

}