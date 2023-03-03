package com.htd.kasir

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.htd.kasir.databinding.ActivityMainBinding
import com.htd.kasir.model.Penjualan
import com.htd.kasir.rest.ApiClient
import com.htd.kasir.rest.ApiInterface
import com.htd.kasir.rest.response.TransactionsResponse
import com.htd.kasir.util.CurrencyFormat
import com.htd.kasir.viewmodel.MainViewModel
import com.kasir.adapter.PenjualanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var tokenStored: SharedPreferences
    lateinit var mApiInterface: ApiInterface
    lateinit var userLoggedIn: SharedPreferences

    var transaksiAdapter: PenjualanAdapter = PenjualanAdapter()
    var mainViewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observe()
        api()
        listener()
    }

    fun init(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        tokenStored = getSharedPreferences("token_data", MODE_PRIVATE)
        mApiInterface = ApiClient.client(tokenStored.getString("business_url",null)).create(ApiInterface::class.java)
        userLoggedIn = getSharedPreferences("login_data", MODE_PRIVATE)

        binding.rvTransaksi.layoutManager = LinearLayoutManager(this)
        binding.rvTransaksi.adapter = transaksiAdapter
    }

    fun observe(){
        mainViewModel.transaksi.observe(this) { data ->
            transaksiAdapter.data = data
            transaksiAdapter.notifyDataSetChanged()
        }

        mainViewModel.omset.observe(this) {binding.omset.text = it}
    }

    fun api(){
        mApiInterface.omset(userLoggedIn.getString("jwt",null)!!).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.body() != null) {
                    var res = Gson().toJsonTree(response.body()).asJsonObject
                    mainViewModel.omset.postValue(CurrencyFormat.format(res.get("data").asDouble))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d(packageName, t.toString())
            }
        })

        mApiInterface.transactions(userLoggedIn.getString("jwt",null)!!).enqueue(object : Callback<TransactionsResponse>{
            override fun onResponse(call: Call<TransactionsResponse>, response: Response<TransactionsResponse>) {
                val res = response.body()
                Log.d(packageName, res.toString())
                if (res != null) {
                    mainViewModel.transaksi.postValue(res.data)
                }
            }

            override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                Log.d(packageName, t.toString())
            }
        })
    }

    fun listener(){
        binding.logout.setOnClickListener(this)
        binding.kasir.setOnClickListener(this)
        binding.penjualan.setOnClickListener(this)
        binding.pengaturan.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.kasir.id -> {
                startActivity(Intent(applicationContext,KasirActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            }
            binding.penjualan.id -> {
                startActivity(Intent(applicationContext,PenjualanActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            }
            binding.logout.id->{
                userLoggedIn.edit().clear().apply()
                startActivity(Intent(applicationContext,SplashScreenActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()
            }
        }
    }
}