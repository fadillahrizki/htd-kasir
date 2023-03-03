package com.htd.kasir

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.htd.kasir.rest.ApiInterface
import com.htd.kasir.databinding.ActivityLoginBinding
import com.htd.kasir.model.LoginModel
import com.htd.kasir.rest.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    lateinit var mApiInterface: ApiInterface
    lateinit var tokenStored: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        tokenStored = getSharedPreferences("token_data", MODE_PRIVATE)
        mApiInterface = ApiClient.client(tokenStored.getString("business_url",null)).create(ApiInterface::class.java)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnLogin.id -> login()
        }
    }

    private fun login() {
        var allTrue = true
        if (binding.username.text.toString().isEmpty()) {
            binding.username.error = "Username tidak boleh kosong"
            allTrue = false
        }
        if (binding.password.text.toString().isEmpty()) {
            binding.password.error = "Kata Sandi tidak boleh kosong"
            allTrue = false
        }
        if (allTrue) {
            binding.btnLogin.setText("Loading...")
            val loginData = getSharedPreferences("login_data", MODE_PRIVATE)
            val username: String = binding.username.text.toString()
            val password: String = binding.password.text.toString()
            mApiInterface.login(username, password).enqueue(object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    if (response.body() == null) {
                        binding.cardError.setVisibility(View.VISIBLE)
                        binding.tvError.setText("Username / Password tidak sesuai!")
                    } else {
                        var res = Gson().toJsonTree(response.body()).asJsonObject
                        val editor = loginData.edit()
                        editor.putString("jwt", "Bearer "+res.get("data").asString)
                        editor.apply()
                        binding.cardError.setVisibility(View.GONE)
                        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    binding?.btnLogin.setText("Masuk")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d(packageName, t.toString())
                    binding.btnLogin.setText("Masuk")
                }
            })
        }
    }
}
