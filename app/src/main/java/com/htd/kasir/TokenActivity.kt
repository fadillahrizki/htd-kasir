package com.htd.kasir

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.htd.kasir.databinding.ActivityTokenBinding
import com.htd.kasir.rest.ApiInterface
import com.htd.kasir.rest.ApiClient
import com.htd.kasir.rest.ApiCrm
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityTokenBinding
    lateinit var mApiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        mApiInterface = ApiCrm.client!!.create(ApiInterface::class.java)
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnSubmit.id -> submit()
        }
    }

    private fun submit() {
        var allTrue = true
        if (binding.token.text.toString().isEmpty()) {
            binding.token.error = "Token tidak boleh kosong"
            allTrue = false
        }
        if (allTrue) {
            binding.btnSubmit.setText("Loading...")
            val tokenData = getSharedPreferences("token_data", MODE_PRIVATE)
            val token: String = binding.token.text.toString()
            mApiInterface.getBusiness(token,"minipos").enqueue(object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    var res = Gson().toJsonTree(response.body()).asJsonObject

                    if (res.get("status").asString == "fail") {
                        binding.cardError.setVisibility(View.VISIBLE)
                        binding.tvError.setText(res.get("message").asString)
                    } else {
                        val editor = tokenData.edit()
                        val data = res.get("data").asJsonObject
                        editor.putString("name", data.get("name").asString)
                        editor.putString("phone", data.get("phone").asString)
                        editor.putString("address", data.get("address").asString)
                        editor.putString("token", data.get("token").asString)
                        editor.putString("business_url", data.get("business_url").asString)
                        editor.putString("package_name", data.get("package_name").asString)
                        editor.putString("note", data.get("note").asString)
                        editor.apply()
                        binding.cardError.setVisibility(View.GONE)
                        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    binding?.btnSubmit.setText("Masuk")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d(packageName, t.toString())
                    binding.btnSubmit.setText("Masuk")
                }
            })
        }
    }
}
