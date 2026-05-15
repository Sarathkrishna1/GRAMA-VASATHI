package com.example.gramavasathi

import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class LocalImageSliderAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<LocalImageSliderAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.ivSliderImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_slider_image, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(images[position])
    }

    override fun getItemCount() = images.size
}