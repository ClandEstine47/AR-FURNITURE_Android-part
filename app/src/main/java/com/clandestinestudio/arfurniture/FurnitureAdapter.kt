package com.clandestinestudio.arfurniture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass
import com.squareup.picasso.Picasso

class FurnitureAdapter(private val context: Context, private var items: ArrayList<FurnitureModelClass>) :
    RecyclerView.Adapter<FurnitureAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private lateinit var filteredList: ArrayList<FurnitureModelClass>

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setFilteredList(items: ArrayList<FurnitureModelClass>){
        this.items = items
        this.filteredList = items
        notifyDataSetChanged()
    }

    fun getFilteredList(): ArrayList<FurnitureModelClass> {
        return filteredList
    }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.furniture_card_layout,
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.tvName.text = item.name
        Picasso.get().load(item.image_url).into(holder.imageView)
//        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.card_anim))

//        animation stuff for card views
        val animation = AnimationUtils.loadAnimation(context, R.anim.card_anim)
        val startOffSet = position * 100

        animation.startOffset = startOffSet.toLong()

        holder.cardView.startAnimation(animation)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvName : TextView = view.findViewById(R.id.tv_name)
        val imageView: ImageView = view.findViewById(R.id.img_view)
        val cardView: CardView = view.findViewById(R.id.furnitureCardView)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}