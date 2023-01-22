package com.clandestinestudio.arfurniture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso
import kotlin.concurrent.timer

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var isFavorite: Boolean = false

        val furnitureNameView: TextView = findViewById(R.id.tv_name_detailsPage)
        val furnitureCategoryView: TextView = findViewById(R.id.tv_category_detailsPage)
//        val furnitureImageView : ImageView = findViewById(R.id.iv_detailsPage)
        val furnitureDescriptionView: TextView = findViewById(R.id.tv_description_detailsPage)
        val imageSlider: ImageSlider = findViewById(R.id.image_slider)

        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieFavorite)

        val bundle: Bundle? = intent.extras
        val furnitureName = bundle!!.getString("name")
        val furnitureCategory = bundle!!.getString("category")
        val furnitureImageUrl = bundle!!.getString("image_url")
        val furnitureDescription = bundle!!.getString("description")
        val furnitureDimensionImgUrl = bundle!!.getString("dimensions")

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(furnitureImageUrl))
        imageList.add(SlideModel(furnitureDimensionImgUrl))

        furnitureNameView.text = furnitureName
        furnitureCategoryView.text = furnitureCategory
        furnitureDescriptionView.text = furnitureDescription
//        Picasso.get().load(furnitureImageUrl).into(furnitureImageView)
        imageSlider.setImageList(imageList)

        lottieAnimationView.setOnClickListener {
            if (isFavorite) {
                lottieAnimationView.setMinAndMaxProgress(0.5f, 1.0f)
                lottieAnimationView.playAnimation()
                isFavorite = false

            } else {
                lottieAnimationView.setMinAndMaxProgress(0.0f, 0.5f)
                lottieAnimationView.playAnimation()
                isFavorite = true
            }

        }
    }
}