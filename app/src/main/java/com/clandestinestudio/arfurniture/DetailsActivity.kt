package com.clandestinestudio.arfurniture

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.clandestinestudio.arfurniture.Model.FavoriteFurniture
import com.clandestinestudio.arfurniture.Model.SharedPreferencesManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso
import kotlin.concurrent.timer

class DetailsActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var furniture: FavoriteFurniture
    private var isFavorite : Boolean = false
    private fun updateFavoriteAnimation(isFavorite: Boolean) {
        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieFavorite)
        if (isFavorite) {
            lottieAnimationView.progress = 0.5f
        } else {
            lottieAnimationView.progress = 1.0f
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val furnitureNameView: TextView = findViewById(R.id.tv_name_detailsPage)
        val furnitureCategoryView: TextView = findViewById(R.id.tv_category_detailsPage)
//        val furnitureImageView : ImageView = findViewById(R.id.iv_detailsPage)
        val furnitureDescriptionView: TextView = findViewById(R.id.tv_description_detailsPage)
        val imageSlider: ImageSlider = findViewById(R.id.image_slider)

        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieFavorite)

        val bundle: Bundle? = intent.extras
        furniture = FavoriteFurniture(
            id = bundle!!.getString("id")!!,
            name = bundle.getString("name")!!,
            description = bundle.getString("description")!!,
            image_url = bundle.getString("image_url")!!,
            dimensions = bundle.getString("dimensions")!!,
            category = bundle.getString("category")!!
        )

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(furniture.image_url))
//      imageList.add(SlideModel(furnitureDimensionImgUrl))
        furnitureNameView.text = furniture.name
        furnitureCategoryView.text = furniture.category
        furnitureDescriptionView.text = furniture.description
//       Picasso.get().load(furnitureImageUrl).into(furnitureImageView)
        imageSlider.setImageList(imageList)

        sharedPreferencesManager = SharedPreferencesManager(this)
        val favoriteFurnitures = sharedPreferencesManager.getFavoriteFurnitureList()
        isFavorite = favoriteFurnitures.any { it.id == furniture.id }
        if(isFavorite) {
            lottieAnimationView.progress = 0.5f
        }else {
            lottieAnimationView.progress = 1.0f
        }


        lottieAnimationView.setOnClickListener {
                if (isFavorite) {
                    // Remove favorite furniture from SharedPreferences
                    sharedPreferencesManager.removeFavoriteFurniture(furniture)
                    // Update animation to un-favorite state
                    lottieAnimationView.setMinAndMaxProgress(0.5f, 1.0f)
                    lottieAnimationView.playAnimation()
                    isFavorite = false

                } else {
                    // Add current furniture as favorite to SharedPreferences
                    sharedPreferencesManager.saveFavoriteFurniture(furniture)
                    // Update animation to favorite state
                    lottieAnimationView.setMinAndMaxProgress(0.0f, 0.5f)
                    lottieAnimationView.playAnimation()
                    isFavorite = true
                }
            }
    }
    override fun onResume() {
        super.onResume()
        val favoriteFurnitures = sharedPreferencesManager.getFavoriteFurnitureList()
        isFavorite = favoriteFurnitures.any { it.id == furniture.id }
        updateFavoriteAnimation(isFavorite)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSwipeLeft(this@DetailsActivity)
    }
}