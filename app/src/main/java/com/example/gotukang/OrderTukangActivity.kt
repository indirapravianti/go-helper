package com.example.gotukang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gotukang.models.AddOrderRequest
import com.example.gotukang.networks.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_tukang_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderTukangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukang_profile)
        val tukangName = intent.getStringExtra("TUKANG_USERNAME")
        val username = intent.getStringExtra("USER_USERNAME")

        putOrderButton.setOnClickListener {
            if(orderLocationEditText.text.isNotEmpty()) {
                progressBarOrder.visibility = View.VISIBLE
                val orderRequest = AddOrderRequest(tukangName, orderLocationEditText.text.toString(), username)
                RetrofitBuilder().getService().addOrderToTukang(orderRequest).enqueue(object:
                    Callback<List<String>> {
                    override fun onFailure(call: Call<List<String>>, t: Throwable) {
                        progressBarOrder.visibility = View.GONE
                        Toast.makeText(applicationContext, "Gagal membuat pesanan", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<List<String>>,
                        response: Response<List<String>>
                    ) {
                        progressBarOrder.visibility = View.GONE
                        Toast.makeText(applicationContext, "Berhasil membuat pesanan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
            } else {
                Toast.makeText(this, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}