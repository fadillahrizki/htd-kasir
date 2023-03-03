package com.htd.kasir

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.htd.kasir.databinding.ActivitySplashScreenBinding
import com.htd.kasir.util.DetectConnection.checkInternetConnection

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var tokenStored: SharedPreferences
    private lateinit var userLoggedIn: SharedPreferences
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        tokenStored = getSharedPreferences("token_data", MODE_PRIVATE)
        userLoggedIn = getSharedPreferences("login_data", MODE_PRIVATE)
        Handler().postDelayed({
            if (!checkInternetConnection(this@SplashScreenActivity)) {
                val alertDialog = AlertDialog.Builder(this@SplashScreenActivity).create()
                alertDialog.setTitle("Tidak ada koneksi internet")
                alertDialog.setMessage("Cek koneksi internet anda dan coba lagi!")
                alertDialog.setButton(
                    DialogInterface.BUTTON_POSITIVE, "Coba Lagi"
                ) { dialog, which ->
                    finish()
                    startActivity(intent)
                }
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                if(!tokenStored.contains("token")){
                    val intent = Intent(this@SplashScreenActivity, TokenActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    if (!userLoggedIn.contains("jwt")) {
                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }, 1000)
    }
}