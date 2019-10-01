package adapter

import adapter.PackageAdapter.MyViewHolder
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.simsappkotlin.R
import model.PackageModel
import java.util.*

class PackageAdapter(
    internal var context: Context,
    private val packageModelArrayList: ArrayList<PackageModel>
) : RecyclerView.Adapter<MyViewHolder>() {
    internal var showingfirst = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expandable_package, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val modelfoodrecycler = packageModelArrayList[position]

        holder.hide.text = modelfoodrecycler.hide

        holder.hide.setOnClickListener {
            if (showingfirst) {
                holder.hide.text = "Details"
                holder.hide.textSize = 15f
                holder.hide.setTextColor(Color.parseColor("#0091ea"))
                showingfirst = false
            } else {
                holder.hide.text = "Hide"
                holder.hide.textSize = 15f
                holder.hide.setTextColor(Color.parseColor("#0091ea"))
                showingfirst = true
            }
        }


    }

    override fun getItemCount(): Int {
        return packageModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var hide: TextView

        init {
            hide = itemView.findViewById(R.id.hide)
        }
    }
}