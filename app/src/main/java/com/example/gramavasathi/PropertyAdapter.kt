package com.example.gramavasathi

import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PropertyAdapter(private val propertyList: List<Property>) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvPropName)
        val loc: TextView = view.findViewById(R.id.tvPropLocation)
        val price: TextView = view.findViewById(R.id.tvPropPrice)
        val image: ImageView = view.findViewById(R.id.ivPropImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prop = propertyList[position]
        holder.name.text = prop.name
        holder.loc.text = "📍 ${prop.location}"
        holder.price.text = "₹${prop.price}/night"

        // IMAGE LOGIC: Check if it's local or cloud
        if (prop.localImage != 0) {
            // It's a featured property from Drawables
            holder.image.setImageResource(prop.localImage)
        } else if (prop.images.isNotEmpty()) {
            // It's a cloud property from Firebase (Base64)
            try {
                val imageBytes = Base64.decode(prop.images[0], Base64.DEFAULT)
                Glide.with(holder.itemView.context).asBitmap().load(imageBytes).into(holder.image)
            } catch (e: Exception) {
                holder.image.setImageResource(R.drawable.farm1)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("FARM_DATA", prop)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = propertyList.size
}