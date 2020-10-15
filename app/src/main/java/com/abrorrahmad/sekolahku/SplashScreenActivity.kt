package com.abrorrahmad.sekolahku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //hide navbar / menghilangkan Judul paling atas
        supportActionBar?.hide()

        //update progress
        updateProgress()
    }

    fun updateProgress(){
        var handler = Handler()
        var run = Runnable {
            var progressBar: Int = labelProgressBar.progress
            labelProgressBar.progress = progressBar + 10
            if (progressBar < 100){
                updateProgress()
            }else{
                var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        handler.postDelayed(run, 300)
    }
}