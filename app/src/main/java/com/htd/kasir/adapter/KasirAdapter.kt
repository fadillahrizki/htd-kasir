package com.kasir.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.htd.kasir.R
import com.htd.kasir.model.Penjualan
import com.htd.kasir.model.Product
import com.htd.kasir.util.CurrencyFormat

class KasirAdapter() :  RecyclerView.Adapter<KasirAdapter.MyViewHolder>() {

    var data: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dt = data[position]
        holder.tvName.text = dt.name
        holder.tvPrice.text = CurrencyFormat.format(0.toDouble())
        holder.tvStock.text = dt.stock
        holder.itemView.setOnClickListener {
            Log.d("TEST","ini test")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var tvName: TextView
        var tvPrice: TextView
        var tvStock: TextView

        init {
            img = itemView.findViewById(R.id.image)
            tvName = itemView.findViewById(R.id.name)
            tvPrice = itemView.findViewById(R.id.price)
            tvStock = itemView.findViewById(R.id.stock)
        }
    }
}