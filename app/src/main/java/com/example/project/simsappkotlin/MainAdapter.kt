package com.example.project.simsappkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lead_row.view.*

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    val pictureTitle = listOf("Ke-1", "Ke-2", "Ke-3", "Ke-4", "Dst~")

    //    numberOfItems
    override fun getItemCount(): Int {
        return homeFeed.lead.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
//        untuk memunculkan halaman view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.lead_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val pictureT = homeFeed.lead.get(position)
        holder.view.lead_text_view.text = pictureT.lead_id
        holder.view.opp_text_view.text = pictureT.opp_name
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)