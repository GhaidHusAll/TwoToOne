package com.example.twotoone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_2.view.*

class RecycleViewAdapter(private val guesses : ArrayList<String>): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.row_2,
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = guesses[position]
       holder.itemView.apply {
           tvItem.text = item
       }
    }

    override fun getItemCount() = guesses.size


}