package com.dza.komikara

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dza.komikara.databinding.ActivitySplashScreenBinding
import com.dza.komikara.user.UserMain

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val SPLASH_DISPLAY_LENGTH = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appLogo.alpha = 0f

        binding.appLogo.animate().setDuration(4000).alpha(1f).withEndAction() {
            val mainIntent = Intent(this@SplashScreen, RegisterActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}