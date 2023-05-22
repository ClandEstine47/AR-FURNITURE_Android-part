package com.clandestinestudio.arfurniture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class OpenUnityActivity : UnityPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_unity)

        val message = intent.getStringExtra("message")
        if (message != null) {
            val intent = Intent(this, UnityPlayerActivity::class.java)
            startActivity(intent)
            UnityPlayer.UnitySendMessage("DataManager", "ReceivedMessage", message)
        }
    }
}