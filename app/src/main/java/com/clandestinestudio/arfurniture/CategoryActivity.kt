package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val rvCategoryList: RecyclerView = findViewById(R.id.rvCategoryFurnitureList)

        val furnitureList = intent.getParcelableArrayListExtra<FurnitureModelClass>("categorizedFurnitureList")

        rvCategoryList.layoutManager = GridLayoutManager(this, 2)
        val itemAdapter = FurnitureAdapter(this@CategoryActivity,
        furnitureList as ArrayList<FurnitureModelClass>)
        rvCategoryList.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object : FurnitureAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@CategoryActivity, "you have clicked on item no. $position", Toast.LENGTH_SHORT).show()
            }
        })
    }
}