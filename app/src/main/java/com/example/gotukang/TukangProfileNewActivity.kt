package com.example.gotukang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gotukang.models.FindTukangUsernameRequest
import com.example.gotukang.models.TukangListItem
import com.example.gotukang.networks.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_tukang_profile_new.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class TukangProfileNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukang_profile_new)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }

        val tukangName = intent.getStringExtra("TUKANG_USERNAME")
        val username = intent.getStringExtra("USER_USERNAME")
        val harga = intent.getStringExtra("HARGA").toInt()
        val jasa = intent.getStringExtra("JASA")

        tvRating.text = intent.getStringExtra("REVIEW")
        tvLocation.text = intent.getStringExtra("LOKASI")
        tvJasaTukang.text = jasa
        tvTukangName.text = tukangName
        titleJasa.text = "Jasa $tukangName"

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val price = formatRupiah.format(harga).replace("Rp", "Rp ").replace(",00", "")

        tvPrice.text = price

        btnOrder.setOnClickListener {
            val i = Intent(it.context, CheckoutActivity::class.java)
            i.putExtra("USER_USERNAME", username)
            i.putExtra("TUKANG_USERNAME", tukangName)
            i.putExtra("JASA", jasa)
            i.putExtra("HARGA", harga)
            it.context.startActivity(i)
        }
    }
}