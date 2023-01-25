package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.hide()
        val categoryTitleTV: TextView = findViewById(R.id.tv_category_title)
        val rvCategoryList: RecyclerView = findViewById(R.id.rvCategoryFurnitureList)

        val categFurnitureList = intent.getParcelableArrayListExtra<FurnitureModelClass>("categorizedFurnitureList")
        categoryTitleTV.text = categFurnitureList?.get(0)?.category + "s"
        rvCategoryList.layoutManager = GridLayoutManager(this, 2)
        val itemAdapter = FurnitureAdapter(this@CategoryActivity,
        categFurnitureList as ArrayList<FurnitureModelClass>)
        rvCategoryList.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object : FurnitureAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
              //   Toast.makeText(this@CategoryActivity, "you have clicked on item no. $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@CategoryActivity, DetailsActivity::class.java)
                intent.putExtra("id", categFurnitureList[position].id)
                intent.putExtra("name", categFurnitureList[position].name)
                intent.putExtra("image_url", categFurnitureList[position].image_url)
                intent.putExtra("dimensions", categFurnitureList[position].dimensions)
                intent.putExtra("description", categFurnitureList[position].description)
                intent.putExtra("category", categFurnitureList[position].category)
                startActivity(intent)
                Animatoo.animateSwipeRight(this@CategoryActivity)
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateZoom(this@CategoryActivity)
    }
}