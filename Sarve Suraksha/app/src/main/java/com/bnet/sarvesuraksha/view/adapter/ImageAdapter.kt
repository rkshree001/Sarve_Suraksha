package com.bnet.sarvesuraksha.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.bnet.savresuraksha.R

class ImageAdapter(private val context: Context) : PagerAdapter() {

    override fun getCount(): Int {
        return 4
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view: View = when (position) {
            0 -> inflater.inflate(R.layout.item_layout1, container, false)
            1 -> inflater.inflate(R.layout.item_layout2, container, false)
            2 -> inflater.inflate(R.layout.item_layout3, container, false)
            3 -> inflater.inflate(R.layout.item_layout4, container, false)
            else -> throw IllegalStateException("Unexpected position: $position")
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view == `object`
    }
}