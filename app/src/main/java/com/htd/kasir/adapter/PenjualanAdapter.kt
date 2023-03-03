package com.kasir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.htd.kasir.R
import com.htd.kasir.model.Penjualan
import com.htd.kasir.util.CurrencyFormat

class PenjualanAdapter() :  RecyclerView.Adapter<PenjualanAdapter.MyViewHolder>() {

    var data: ArrayList<Penjualan> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.penjualan_item, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dt = data[position]
        holder.tvInvoice.text = dt.invoice
        holder.tvStatus.text = dt.status
        holder.tvDate.text = dt.date
        holder.tvTotal.text = CurrencyFormat.format(dt.total.toDouble())
        holder.tvNotes.text = if(dt.notes != null) dt.notes else "-"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvInvoice: TextView
        var tvStatus: TextView
        var tvDate: TextView
        var tvTotal: TextView
        var tvNotes: TextView

        init {

            tvInvoice = itemView.findViewById(R.id.invoice)
            tvStatus = itemView.findViewById(R.id.status)
            tvDate = itemView.findViewById(R.id.date)
            tvTotal = itemView.findViewById(R.id.total)
            tvNotes = itemView.findViewById(R.id.notes)
        }
    }
}