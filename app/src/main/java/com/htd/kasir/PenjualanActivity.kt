package com.htd.kasir

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htd.kasir.databinding.ActivityPenjualanBinding
import com.htd.kasir.model.Penjualan
import com.htd.kasir.rest.ApiClient
import com.htd.kasir.rest.ApiInterface
import com.htd.kasir.rest.response.TransactionsResponse
import com.htd.kasir.viewmodel.MainViewModel
import com.kasir.adapter.PenjualanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenjualanActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityPenjualanBinding
    lateinit var mApiInterface: ApiInterface
    lateinit var tokenStored: SharedPreferences
    lateinit var userLoggedIn: SharedPreferences
    var penjualanAdapter: PenjualanAdapter = PenjualanAdapter()
    var mainViewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observe()
        api()
        listener()
    }

    fun init(){
        binding = ActivityPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        tokenStored = getSharedPreferences("token_data", MODE_PRIVATE)
        mApiInterface = ApiClient.client(tokenStored.getString("business_url",null)).create(ApiInterface::class.java)
        userLoggedIn = getSharedPreferences("login_data", MODE_PRIVATE)

        binding.rvPenjualan.layoutManager = LinearLayoutManager(this)
        binding.rvPenjualan.adapter = penjualanAdapter
    }

    fun observe(){
        mainViewModel.penjualan.observe(this) { data ->
            penjualanAdapter.data = data
            penjualanAdapter.notifyDataSetChanged()
        }
    }

    fun api(){
        mApiInterface.transactions(userLoggedIn.getString("jwt",null)!!).enqueue(object :
            Callback<TransactionsResponse> {
            override fun onResponse(call: Call<TransactionsResponse>, response: Response<TransactionsResponse>) {
                val res = response.body()
                Log.d(packageName, res.toString())
                if (res != null) {
                    mainViewModel.penjualan.postValue(res.data)
                }
            }

            override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                Log.d(packageName, t.toString())
            }
        })
    }

    fun listener(){
        binding.back.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.back.id -> {
                onBackPressed()
            }
        }
    }
}