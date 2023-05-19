package com.clandestinestudio.arfurniture

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass
import com.unity3d.player.UnityPlayerActivity
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var rvRecyclerView: RecyclerView
    private lateinit var adapter: FurnitureAdapter
    private lateinit var categoryHorizontalScrollView: HorizontalScrollView
    private lateinit var categoryHeadingTextView: TextView
    private lateinit var exploreHeadingTextView: TextView
    private var furnitureList : ArrayList<FurnitureModelClass> = ArrayList()
    private var categorizedList: ArrayList<FurnitureModelClass> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.text_input_area)
        searchView.clearFocus()
        rvRecyclerView = findViewById(R.id.rvFurnitureList)
        categoryHorizontalScrollView = findViewById(R.id.horizontalScrollView)
        categoryHeadingTextView = findViewById(R.id.category_heading_tv)
        exploreHeadingTextView = findViewById(R.id.exploreHeadingTv)

//        startActivity(Intent(this, UnityHandlerActivity::class.java))
        val bedCard = findViewById<CardView>(R.id.cv_beds)
        val sofaCard = findViewById<CardView>(R.id.cv_sofas)
        val chairCard = findViewById<CardView>(R.id.cv_chairs)
        val tableCard = findViewById<CardView>(R.id.cv_tables)
        val rvRecyclerView = findViewById<RecyclerView>(R.id.rvFurnitureList)

        val favButton = findViewById<Button>(R.id.btn_fav)

        favButton.setOnClickListener{
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            Animatoo.animateZoom(this@MainActivity)
        }


        //parsing the json from assets folder for the furniture data
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
                val itemFolderName = furniture.getString("itemFolderName")
                val furnitureDetails =
                    FurnitureModelClass(id, name, category, image_url, dimensions, description, itemFolderName)


                furnitureList.add(furnitureDetails)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        rvRecyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = FurnitureAdapter(this, furnitureList)
        rvRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : FurnitureAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val selectedItem = if (searchView.query.isNullOrBlank() && !searchView.hasFocus()) {
                    furnitureList[position]
                } else {
                    adapter.getFilteredList()[position]
                }
                val intent = Intent(this@MainActivity, DetailsActivity::class.java).apply {
                    putExtra("id", selectedItem.id)
                    putExtra("name", selectedItem.name)
                    putExtra("image_url", selectedItem.image_url)
                    putExtra("dimensions", selectedItem.dimensions)
                    putExtra("description", selectedItem.description)
                    putExtra("category", selectedItem.category)
                    putExtra("itemFolderName", selectedItem.itemFolderName)
                }
                startActivity(intent)
                Animatoo.animateSwipeRight(this@MainActivity)
            }

//            override fun onItemClick(position: Int) {
////                Toast.makeText(this@MainActivity, "you have clicked on item no: $position", Toast.LENGTH_SHORT).show()
//
//                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
//                intent.putExtra("id", furnitureList[position].id)
//                intent.putExtra("name", furnitureList[position].name)
//                intent.putExtra("image_url", furnitureList[position].image_url)
//                intent.putExtra("dimensions", furnitureList[position].dimensions)
//                intent.putExtra("description", furnitureList[position].description)
//                intent.putExtra("category", furnitureList[position].category)
//                intent.putExtra("itemFolderName", furnitureList[position].itemFolderName)
//                startActivity(intent)
//                Animatoo.animateSwipeRight(this@MainActivity)
//            }


        })

        // search view backend code
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        //hides the categories layout when the text input field is clicked
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                categoryHorizontalScrollView.visibility = View.GONE
                categoryHeadingTextView.visibility = View.GONE
                exploreHeadingTextView.text = "Try Searching for furniture or keywords."
                exploreHeadingTextView.textSize = 12f
                exploreHeadingTextView.setTextColor(Color.GRAY)
            } else {
                categoryHorizontalScrollView.visibility = View.VISIBLE
                categoryHeadingTextView.visibility = View.VISIBLE
                exploreHeadingTextView.text = "Explore"
                exploreHeadingTextView.textSize = 16f
                exploreHeadingTextView.setTextColor(Color.parseColor("#465C8C"))
                searchView.setQuery("", false)
            }
        }


        fun openCategoryActivity(){
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putParcelableArrayListExtra("categorizedFurnitureList", ArrayList(categorizedList))
            startActivity(intent)
            Animatoo.animateZoom(this@MainActivity)
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

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<FurnitureModelClass>()

            for (i in furnitureList) {
                if(i.name?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }

            }


            adapter.setFilteredList(filteredList)

        }

    }

    private fun getJSONFromAssets(): String? {

        val jsonString: String = assets.open("furniture_data.json").bufferedReader().use {
            it.readText()}
        return jsonString

    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.setQuery("", false)
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
}