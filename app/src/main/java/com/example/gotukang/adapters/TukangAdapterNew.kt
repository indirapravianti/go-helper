package com.example.gotukang.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotukang.R
import com.example.gotukang.TukangProfileNewActivity
import com.example.gotukang.models.TukangListItem
import kotlinx.android.synthetic.main.item_tukang.view.*

class TukangAdapterNew(private val usernameUs: String, private val tukangs: List<TukangListItem?>?): RecyclerView.Adapter<TukangAdapterNew.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(usernameUs: String, get: TukangListItem?) {
            itemView.tvTukangName.text = get?.username
            itemView.tvLocation.text = get?.location

            itemView.setOnClickListener {
                if (get != null){
                    val i = Intent(it.context, TukangProfileNewActivity::class.java)
                    i.putExtra("USER_USERNAME", usernameUs)
                    i.putExtra("TUKANG_USERNAME", get.username)
                    i.putExtra("JASA", get.specialization)
                    i.putExtra("HARGA", get.price)
                    i.putExtra("REVIEW", get.review)
                    i.putExtra("LOKASI", get.location)
                    it.context.startActivity(i)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val tukangView = inflater.inflate(R.layout.item_tukang, parent, false)
        return ViewHolder(tukangView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usernameUs, tukangs?.get(position))
    }

    override fun getItemCount(): Int {
        return tukangs?.size ?: 0
    }
}