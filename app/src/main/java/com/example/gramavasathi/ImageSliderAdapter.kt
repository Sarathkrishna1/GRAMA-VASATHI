package com.example.gramavasathi

import android.util.Base64
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.ivSliderImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_slider_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bytes = Base64.decode(images[position], Base64.DEFAULT)
        Glide.with(holder.itemView.context).asBitmap().load(bytes).into(holder.img)
    }

    override fun getItemCount() = images.size
}