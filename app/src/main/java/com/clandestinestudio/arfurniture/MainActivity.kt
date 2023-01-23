package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startActivity(Intent(this, UnityHandlerActivity::class.java))
        val bedCard = findViewById<CardView>(R.id.cv_beds)
        val sofaCard = findViewById<CardView>(R.id.cv_sofas)
        val chairCard = findViewById<CardView>(R.id.cv_chairs)
        val tableCard = findViewById<CardView>(R.id.cv_tables)
        val rvRecyclerView = findViewById<RecyclerView>(R.id.rvFurnitureList)
        val furnitureList : ArrayList<FurnitureModelClass> = ArrayList()
        var categorizedList: ArrayList<FurnitureModelClass> = ArrayList()
        try {

            val obj = JSONObject(getJSONFromAssets())

            val furnitureArray = obj.getJSONArray("furniture")

            for (i in 0 until furnitureArray.length()) {

                val furniture = furnitureArray.getJSONObject(i)
                val id = furniture.getString("id")
                val name = furniture.getString("name")
                val category = furniture.getString("category")
                val image_url = furniture.getString("image_url")
                val dimensions = furniture.getString("dimensions")
                val description = furniture.getString("description")
                val furnitureDetails =
                    FurnitureModelClass(id, name, category, image_url, dimensions, description)


                furnitureList.add(furnitureDetails)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        rvRecyclerView.layoutManager = GridLayoutManager(this,2)
        val itemAdapter = FurnitureAdapter(this, furnitureList)
        rvRecyclerView.adapter = itemAdapter
        itemAdapter.setOnItemClickListener(object : FurnitureAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MainActivity, "you have clicked on item no: $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra("id", furnitureList[position].id)
                intent.putExtra("name", furnitureList[position].name)
                intent.putExtra("image_url", furnitureList[position].image_url)
                intent.putExtra("dimensions", furnitureList[position].dimensions)
                intent.putExtra("description", furnitureList[position].description)
                intent.putExtra("category", furnitureList[position].category)
                startActivity(intent)
            }

        })

        fun openCategoryActivity(){
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putParcelableArrayListExtra("categorizedFurnitureList", ArrayList(categorizedList))
            startActivity(intent)
        }
        bedCard.setOnClickListener {
            categorizedList = furnitureList.filter { it.category == "Bed"} as ArrayList<FurnitureModelClass>
            openCategoryActivity()
        }
        sofaCard.setOnClickListener {
            categorizedList = furnitureList.filter { it.category == "Sofa"} as ArrayList<FurnitureModelClass>
            openCategoryActivity()
        }
        chairCard.setOnClickListener {
            categorizedList = furnitureList.filter { it.category == "Chair"} as ArrayList<FurnitureModelClass>
            openCategoryActivity()
        }
        tableCard.setOnClickListener {
            categorizedList = furnitureList.filter { it.category == "Table"} as ArrayList<FurnitureModelClass>
            openCategoryActivity()
        }

    }

    private fun getJSONFromAssets(): String? {

        val jsonString: String = assets.open("furniture_data.json").bufferedReader().use {
            it.readText()}
        return jsonString

    }
}