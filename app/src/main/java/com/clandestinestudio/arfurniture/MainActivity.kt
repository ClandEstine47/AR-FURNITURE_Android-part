package com.clandestinestudio.arfurniture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val rvRecyclerView = findViewById<RecyclerView>(R.id.rvFurnitureList)
        val furnitureList : ArrayList<FurnitureModelClass> = ArrayList()
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
                Toast.makeText(this@MainActivity, "you have clicked on item no: $position", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getJSONFromAssets(): String? {

        val jsonString: String = assets.open("furniture_data.json").bufferedReader().use {
            it.readText()}
        return jsonString

    }
}