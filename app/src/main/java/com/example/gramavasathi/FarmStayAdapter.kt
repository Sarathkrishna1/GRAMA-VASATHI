package com.example.gramavasathi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FarmStayAdapter(
    private val farmList: List<FarmStay>,
    private val onClick: (FarmStay) -> Unit
) : RecyclerView.Adapter<FarmStayAdapter.FarmViewHolder>() {

    class FarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvFarmName)
        val locationText: TextView = view.findViewById(R.id.tvFarmLocation)
        val priceText: TextView = view.findViewById(R.id.tvFarmPrice)
        val farmImage: ImageView = view.findViewById(R.id.imgFarm)
        val ratingText: TextView = view.findViewById(R.id.tvFarmRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_farm_stay, parent, false)
        return FarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) {
        val farm = farmList[position]
        holder.nameText.text = farm.name
        holder.locationText.text = farm.location
        holder.priceText.text = farm.price
        holder.farmImage.setImageResource(farm.imageRes)

        holder.itemView.setOnClickListener { onClick(farm) }
        holder.ratingText.text = farm.rating
    }

    override fun getItemCount() = farmList.size
}