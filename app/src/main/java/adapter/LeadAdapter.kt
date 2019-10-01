package com.example.project.simsappkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expandable_package.view.*

class LeadAdapter(val homeLead: HomeLead) : RecyclerView.Adapter<CustomViewLead>() {

    val pictureTitle = listOf("Ke-1", "Ke-2", "Ke-3", "Ke-4", "Dst~")

    //    numberOfItems
    override fun getItemCount(): Int {
        return homeLead.lead.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewLead {
//        untuk memunculkan halaman view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.expandable_package, parent, false)
        return CustomViewLead(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewLead, position: Int) {
        val pictureT = homeLead.lead.get(position)
        holder.view.lblListHeader.text = pictureT.lead_id
    }
}

class CustomViewLead(val view: View) : RecyclerView.ViewHolder(view)