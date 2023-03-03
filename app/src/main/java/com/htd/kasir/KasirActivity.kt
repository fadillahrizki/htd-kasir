package com.htd.kasir

import android.R
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.htd.kasir.databinding.ActivityKasirBinding
import com.htd.kasir.databinding.ActivityPenjualanBinding
import com.htd.kasir.model.Penjualan
import com.htd.kasir.rest.ApiClient
import com.htd.kasir.rest.ApiInterface
import com.htd.kasir.rest.response.ProductsResponse
import com.htd.kasir.rest.response.TransactionsResponse
import com.htd.kasir.viewmodel.MainViewModel
import com.kasir.adapter.KasirAdapter
import com.kasir.adapter.PenjualanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KasirActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityKasirBinding
    lateinit var mApiInterface: ApiInterface
    lateinit var tokenStored: SharedPreferences
    lateinit var userLoggedIn: SharedPreferences
    lateinit var sheetBehavior: BottomSheetBehavior<*>

    var kasirAdapter: KasirAdapter = KasirAdapter()
    var mainViewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observe()
        api()
        listener()
    }

    fun init(){
        binding = ActivityKasirBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        tokenStored = getSharedPreferences("token_data", MODE_PRIVATE)
        mApiInterface = ApiClient.client(tokenStored.getString("business_url",null)).create(ApiInterface::class.java)
        userLoggedIn = getSharedPreferences("login_data", MODE_PRIVATE)

        sheetBehavior = BottomSheetBehavior.from(binding.layoutBs.bottomSheetLayout)

        binding.rvProduct.layoutManager = GridLayoutManager(this,2)
        binding.rvProduct.adapter = kasirAdapter
    }

    fun observe(){
        mainViewModel.kasir.observe(this) { data ->
            kasirAdapter.data = data
            kasirAdapter.notifyDataSetChanged()
        }
    }

    fun api(){
        mApiInterface.products(userLoggedIn.getString("jwt",null)!!).enqueue(object :
            Callback<ProductsResponse> {
            override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {
                val res = response.body()
                Log.d(packageName, res.toString())
                if (res != null) {
                    mainViewModel.kasir.postValue(res.data)
                }
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                Log.d(packageName, t.toString())
            }
        })
    }

    fun listener(){
        binding.back.setOnClickListener(this)
        binding.layoutBs.bottomSheetHeader.setOnClickListener(this)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.back.id -> {
                onBackPressed()
            }
            binding.layoutBs.bottomSheetHeader.id->{
                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        }
    }
}