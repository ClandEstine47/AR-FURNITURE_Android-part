package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import kotlinx.coroutines.*
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Color

class SplashScreen : AppCompatActivity() {
    private lateinit var gradientTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        gradientTextView = findViewById(R.id.appname)
        val startColor = Color.parseColor("#4056A1")
        val endColor = Color.parseColor("#FF6830")

        val gradient = LinearGradient(
            0f, 0f, gradientTextView.paint.measureText(gradientTextView.text.toString()), 0f,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        gradientTextView.paint.shader = gradient

        CoroutineScope(Dispatchers.Main).launch {
            delay(6000L)
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            Animatoo.animateSplit(this@SplashScreen)
            finish()
        }

    }

}