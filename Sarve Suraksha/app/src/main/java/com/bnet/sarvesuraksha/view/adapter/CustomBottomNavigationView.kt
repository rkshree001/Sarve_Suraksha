package com.bnet.sarvesuraksha.view.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.bnet.savresuraksha.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class CustomBottomNavigationView(context: Context, attrs: AttributeSet?) : BottomNavigationView(context, attrs) {
    private var selectedItemBackground: Drawable? = null

    init {
        selectedItemBackground = ContextCompat.getDrawable(context, R.drawable.nav_item_background)
    }

    @SuppressLint("RestrictedApi")
    override fun onDraw(canvas: Canvas) {
        val menuView = getChildAt(0) as? ViewGroup ?: return
        for (i in 0 until menuView.childCount) {
            val itemView = menuView.getChildAt(i) as? BottomNavigationItemView ?: continue
            if (itemView.itemData?.isChecked == true) {
                selectedItemBackground?.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
                selectedItemBackground?.draw(canvas!!)
            }
        }
        super.onDraw(canvas)
    }
}
