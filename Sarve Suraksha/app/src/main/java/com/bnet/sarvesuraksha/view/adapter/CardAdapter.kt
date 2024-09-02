package com.bnet.sarvesuraksha.view.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.asynctaskcoffee.cardstack.CardContainerAdapter
import com.bnet.savresuraksha.R


class CardAdapter(private val context: Context, private val imageList: List<Int>) : CardContainerAdapter() {

    override fun getCount(): Int = Integer.MAX_VALUE

    override fun getItem(position: Int): Any = imageList[position % imageList.size]

     fun getItemId(position: Int): Long = (position % imageList.size).toLong()

    override fun getView(position: Int): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_item, null)
        val imageView: ImageView = view.findViewById(R.id.cardImage)
        imageView.setImageResource(imageList[position % imageList.size])
        return view
    }
}
