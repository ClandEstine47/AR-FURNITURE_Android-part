package com.clandestinestudio.arfurniture

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.clandestinestudio.arfurniture.Model.FavoriteFurniture
import com.clandestinestudio.arfurniture.Model.FurnitureModelClass
import com.clandestinestudio.arfurniture.Model.SharedPreferencesManager
import nl.joery.animatedbottombar.AnimatedBottomBar

class FavoritesActivity : AppCompatActivity() {
    private lateinit var rvFavoriteList: RecyclerView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private  lateinit var favoriteFurnitureList: List<FavoriteFurniture>
    private lateinit var bottomNavbar : AnimatedBottomBar
    private lateinit var favTextInfo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        supportActionBar?.hide()

        favTextInfo = findViewById(R.id.fav_info)
        rvFavoriteList = findViewById(R.id.rvFavoritesList)

        sharedPreferencesManager = SharedPreferencesManager(this)
        favoriteFurnitureList = sharedPreferencesManager.getFavoriteFurnitureList()
        val furnitureModelList: ArrayList<FurnitureModelClass> = ArrayList()

        for (favoriteFurniture in favoriteFurnitureList) {
            val furnitureModel: FurnitureModelClass = convertToModelClass(favoriteFurniture)
            furnitureModelList.add(furnitureModel)
        }


        // navigation stuff
        bottomNavbar  = findViewById(R.id.bottom_bar)
        bottomNavbar.setOnTabSelectListener(object: AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {

                when (newTab.title) {

                    "Home" -> {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        Animatoo.animateFade(this@FavoritesActivity)

                    }
                    "My Collections" -> {
                        startActivity(Intent(applicationContext, FavoritesActivity::class.java))
                        Animatoo.animateFade(this@FavoritesActivity)

                    }
                    "Search" -> {
                        val mainIntent = Intent(applicationContext, MainActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        mainIntent.putExtra("focusSearchView", true)
                        startActivity(mainIntent)
                        Animatoo.animateFade(this@FavoritesActivity)

                    }

                }

            }
        })


        rvFavoriteList.layoutManager = GridLayoutManager(this, 2)
        val itemAdapter = FurnitureAdapter(this@FavoritesActivity, furnitureModelList )
        rvFavoriteList.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object : FurnitureAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //   Toast.makeText(this@CategoryActivity, "you have clicked on item no. $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@FavoritesActivity, DetailsActivity::class.java)
                intent.putExtra("id", furnitureModelList[position].id)
                intent.putExtra("name", furnitureModelList[position].name)
                intent.putExtra("image_url", furnitureModelList[position].image_url)
                intent.putExtra("dimensions", furnitureModelList[position].dimensions)
                intent.putExtra("description", furnitureModelList[position].description)
                intent.putExtra("category", furnitureModelList[position].category)
                intent.putExtra("itemFolderName", furnitureModelList[position].itemFolderName)

                //sending flag to detail activity to know if this was the previous activity for it
                intent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT

                startActivity(intent)
                Animatoo.animateSwipeRight(this@FavoritesActivity)
            }

        })




    }

    private fun convertToModelClass(favoriteFurniture: FavoriteFurniture): FurnitureModelClass {

        return FurnitureModelClass(
            id = favoriteFurniture.id,
            name = favoriteFurniture.name,
            category = favoriteFurniture.category,
            image_url = favoriteFurniture.image_url,
            dimensions = favoriteFurniture.dimensions,
            description = favoriteFurniture.description,
            itemFolderName = favoriteFurniture.itemFolderName
        )

    }



    override fun onResume() {
        super.onResume()
        favoriteFurnitureList = sharedPreferencesManager.getFavoriteFurnitureList()
        if (favoriteFurnitureList.isEmpty()) {
            favTextInfo.visibility = TextView.VISIBLE
        }else{
            favTextInfo.visibility = TextView.GONE
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateZoom(this@FavoritesActivity)
    }
}