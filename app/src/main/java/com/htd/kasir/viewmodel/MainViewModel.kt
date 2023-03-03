package com.htd.kasir.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.htd.kasir.model.Penjualan
import com.htd.kasir.model.Product

class MainViewModel : ViewModel() {
    var omset : MutableLiveData<String> = MutableLiveData()
    var transaksi : MutableLiveData<ArrayList<Penjualan>> = MutableLiveData()
    var kasir : MutableLiveData<ArrayList<Product>> = MutableLiveData()
    var penjualan : MutableLiveData<ArrayList<Penjualan>> = MutableLiveData()
}