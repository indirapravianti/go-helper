package com.example.gotukang.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotukang.R
import com.example.gotukang.models.TukangListItem
import kotlinx.android.synthetic.main.tukang_info_component.view.*

class TukangAdapters(private val tukangs: List<TukangListItem?>?, private val itemClickListener: (TukangListItem) -> Unit): RecyclerView.Adapter<TukangAdapters.ViewHolder>(){
    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind(get: TukangListItem?, clickListener: (TukangListItem) -> Unit) {
            itemView.tukangNameTextView.text = get?.username
            itemView.tukangLocationTextView.text = get?.location
            itemView.tukangSpecializationTextView.text = get?.specialization
            itemView.tukangReviewTextView.text = get?.review

            itemView.setOnClickListener {
                if (get != null) {
                    clickListener(get)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val tukangView = inflater.inflate(R.layout.tukang_info_component, parent, false)
        return ViewHolder(tukangView)
    }

    override fun getItemCount(): Int {
        return tukangs?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tukangs?.get(position), itemClickListener)
    }
}