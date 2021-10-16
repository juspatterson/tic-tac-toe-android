package app.emojitictactow

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorator(private val mDivider: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        drawHorizontalRectangle(0,parent,canvas)
        drawHorizontalRectangle(1,parent,canvas)

        drawVerticalRectangle(0,parent,canvas)
        drawVerticalRectangle(3,parent,canvas)
    }
    fun drawVerticalRectangle(atChild: Int, parent: RecyclerView, canvas: Canvas){
        val child = parent.getChildAt(atChild)
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val dividerTop =
            child.bottom - 12
        val dividerBottom = dividerTop + mDivider!!.intrinsicHeight
        mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)

        mDivider.draw(canvas)
    }

    fun drawHorizontalRectangle(atChild: Int, parent: RecyclerView, canvas: Canvas){
        var child = parent.getChildAt(atChild)
        var dividerLeft = child.right - 12
        var dividerRight = dividerLeft + mDivider!!.intrinsicHeight
        var dividerTop = parent.paddingTop
        var dividerBottom = parent.bottom
        mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
        mDivider.draw(canvas)
    }

}