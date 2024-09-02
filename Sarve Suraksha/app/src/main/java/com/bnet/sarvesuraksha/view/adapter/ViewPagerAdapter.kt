package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.bnet.savresuraksha.R
import android.widget.ImageView

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val items = listOf(
        R.drawable.landing_page1,
        R.drawable.landing_page2
    )

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }


    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.viewpager_rec_card, container, false)

        val actualPosition = position % items.size
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(items[actualPosition])

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
