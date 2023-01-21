package com.clandestinestudio.arfurniture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass
import com.squareup.picasso.Picasso

class FurnitureAdapter(private val context: Context, private val items: ArrayList<FurnitureModelClass>) :
    RecyclerView.Adapter<FurnitureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.furniture_card_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.tvName.text = item.name
        Picasso.get().load(item.image_url).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvName : TextView = view.findViewById(R.id.tv_name)
        val imageView: ImageView = view.findViewById(R.id.img_view)
    }
}