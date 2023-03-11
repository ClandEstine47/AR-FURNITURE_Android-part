package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import kotlin.concurrent.timer

class DetailsActivity : UnityPlayerActivity() {
    var furnitureFolderName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var isFavorite: Boolean = false

        var btnOpenUnity: Button = findViewById(R.id.btn_OpenUnity)

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
        furnitureFolderName = bundle!!.getString("itemFolderName")

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(furnitureImageUrl))
//        imageList.add(SlideModel(furnitureDimensionImgUrl))

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

        btnOpenUnity.setOnClickListener{
            OpenUnity()
//            var intent = Intent(this, UnityPlayerActivity::class.java)
//            startActivity(intent)
//            UnityPlayer.UnitySendMessage("DataManager", "ReceivedMessage", "Sofa2Seat")
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSwipeLeft(this@DetailsActivity)
    }

    fun OpenUnity(){
        var intent = Intent(this, UnityPlayerActivity::class.java)
        startActivity(intent)
        UnityPlayer.UnitySendMessage("DataManager", "ReceivedMessage", furnitureFolderName)
    }
}