package com.example.whatsappclone.activity


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.whatsappclone.MainActivity
import com.example.whatsappclone.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    var charSequence: CharSequence? = null
    var index = 0
    var delay: Long = 200
    var handler = Handler()
    var textView: TextView? = null
    var binding:ActivitySplashBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        YoYo.with(Techniques.ZoomIn).duration(500).repeat(0).playOn(binding!!.imageView)

       @Suppress("DEPRECATION")

        Handler().postDelayed({
             startActivity(
                Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }, 500)


    }
}