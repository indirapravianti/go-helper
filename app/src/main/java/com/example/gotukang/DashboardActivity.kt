package com.example.gotukang

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gotukang.adapters.TukangAdapters
import com.example.gotukang.models.AddOrderRequest
import com.example.gotukang.models.TukangList
import com.example.gotukang.models.TukangListItem
import com.example.gotukang.networks.RetrofitBuilder
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_order_component.*
import kotlinx.android.synthetic.main.create_order_component.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        username = intent.getStringExtra("EXTRA_USERNAME")
        val linearLayoutManager = LinearLayoutManager(this)
        tukangRecyclerView.layoutManager = linearLayoutManager

        RetrofitBuilder().getService().getTukangList().enqueue(object: Callback<TukangList> {
            override fun onFailure(call: Call<TukangList>, t: Throwable) {
                Toast.makeText(applicationContext, "There's a problem, please try again.", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TukangList>, response: Response<TukangList>) {
                val adapter = TukangAdapters(response.body()?.tukangList) {
                    tukangClickedListener(it)
                }
                tukangRecyclerView.adapter = adapter
                progressBarDashboard.visibility = View.GONE
            }

        })

        //FAB for web view
        fab_webview.setOnClickListener {
//            Log.d("FAB1", "onCreate: +fab clicked")
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }
    }

    private fun tukangClickedListener(tukangItem: TukangListItem) {


        val dialog = AlertDialog.Builder(this).create()
        val dialogView = layoutInflater.inflate(R.layout.create_order_component, null)

        dialogView.tukangNameProfileTextView.text = tukangItem.username
        dialogView.tukangAlamatTextView.text = tukangItem.location
        dialogView.tukangServiceProfileTextView.text = tukangItem.specialization
        dialogView.tukangReviewProfileTextView.text = tukangItem.review

        dialog.setView(dialogView)
        dialog.setCancelable(true)

        dialogView.followButton.setOnClickListener {
            progressBarDashboard.visibility = View.VISIBLE
            Firebase.messaging.subscribeToTopic(tukangItem.username.toString()).addOnCompleteListener { task ->
                progressBarDashboard.visibility = View.GONE
                val message = if(task.isSuccessful) {
                    "Success Subscribed To ${tukangItem.username}"
                } else {
                    "Subscribe Failed, Please Try Again, Error ${task.result}"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        dialogView.orderButton.setOnClickListener {
            val intent = Intent(this, OrderTukangActivity::class.java)
            intent.putExtra("USER_USERNAME", username)
            intent.putExtra("TUKANG_USERNAME", tukangItem.username)
            startActivity(intent)
        }
        dialog.show()
    }
}