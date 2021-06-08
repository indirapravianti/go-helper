package com.example.gotukang

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gotukang.models.AddOrderRequest
import com.example.gotukang.networks.RetrofitBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.MapType
import com.sucho.placepicker.PlacePicker
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_tukang_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class CheckoutActivity : AppCompatActivity() {
    private val PLACE_PICKER_REQUEST = 1000
    private val PERMISSION_REQUEST = 1001
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationManager: LocationManager
    private lateinit var gps_loc: Location
    private lateinit var network_loc: Location
    private lateinit var final_loc: Location
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var mHour: Int = 0
    var mMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val tukangName = intent.getStringExtra("TUKANG_USERNAME")
        val username = intent.getStringExtra("USER_USERNAME")
        val jasa = intent.getStringExtra("JASA")
        val harga = intent.getIntExtra("HARGA", 0)

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val price = formatRupiah.format(harga).replace("Rp", "Rp ").replace(",00", "")

        tvTukangName.text = tukangName
        tvServiceName.text = jasa
        tvPrice.text = price

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getUserCurrentLocation()

        btnChooseLocation.setOnClickListener {
            placePicker()
        }

        btnChooseSchedule.setOnClickListener {
            datePicker()
        }

        btnCheckout.setOnClickListener {
            val time = System.currentTimeMillis()
            val date = Date(time)
            val complete = SimpleDateFormat("MM/DD/YYYY")
            val orderRequest = AddOrderRequest(
                tukangName, tvLocation.text.toString(), username, harga.toString(), complete.format(
                    date
                )
            )
            RetrofitBuilder().getService().addOrderToTukang(orderRequest).enqueue(object :
                Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Gagal membuat pesanan", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Berhasil membuat pesanan",
                        Toast.LENGTH_SHORT
                    ).show()
                    val i = Intent(applicationContext, DashboardActivity::class.java)
                    i.putExtra("EXTRA_USERNAME", username)
                    startActivity(i)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val addressData: AddressData = data!!.getParcelableExtra(Constants.ADDRESS_INTENT)
                tvLocation.text = addressData.addressList!![0].getAddressLine(0)
                latitude = addressData.latitude
                longitude = addressData.longitude
            }
        }
    }

    private fun getUserCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
            return
        }
        fusedLocationProviderClient!!.lastLocation
            .addOnSuccessListener(this, OnSuccessListener<Location> { location: Location? ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            if (checkPermission()) {
                getUserCurrentLocation()
            }
        }
    }

    private fun checkPermission(): Boolean {
        for (mPermission in arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val result = ActivityCompat.checkSelfPermission(this, mPermission)
            if (result == PackageManager.PERMISSION_DENIED) return false
        }
        return true
    }

    private fun placePicker() {
        /*var intent = PlacePicker.IntentBuilder()
            .setLatLong(latitude, longitude)
            .setMapZoom(16.0f)  // Map Zoom Level. Default: 14.0
            .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
            .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
            .setMarkerImageImageColor(R.color.colorPrimary)
            .setMapType(MapType.NORMAL)
            .hideLocationButton(true)   //Hide Location Button (Default: false)
            .disableMarkerAnimation(true)   //Disable Marker Animation (Default: false)
            .build(this)*/

        val intent: Intent
        intent = if (latitude == null && longitude == null) {
            PlacePicker.IntentBuilder()
                .setLatLong(-7.765981, 110.371683)
                .setMapZoom(16.0f) // Map Zoom Level. Default: 14.0
                .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
                .setMarkerImageImageColor(R.color.colorPrimary)
                .setMapType(MapType.NORMAL)
                .hideLocationButton(true) //Hide Location Button (Default: false)
                .disableMarkerAnimation(true) //Disable Marker Animation (Default: false)
                .build(this)
        } else {
            PlacePicker.IntentBuilder()
                .setLatLong(latitude, longitude)
                .setMapZoom(16.0f) // Map Zoom Level. Default: 14.0
                .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
                .setMarkerImageImageColor(R.color.colorPrimary)
                .setMapType(MapType.NORMAL)
                .hideLocationButton(true) //Hide Location Button (Default: false)
                .disableMarkerAnimation(true) //Disable Marker Animation (Default: false)
                .build(this)
        }

        startActivityForResult(intent, PLACE_PICKER_REQUEST)
    }

    private fun datePicker() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                mDay = dayOfMonth
                mMonth = monthOfYear
                mYear = year
                tiemPicker()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun tiemPicker() {
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            this,
            { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                val cal: Calendar = GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute)
                val time = cal.timeInMillis
                val date = Date(time)
                val locale = Locale("id", "ID")
                val complete = SimpleDateFormat("EEEE, d MMMM yyyy", locale)
                val waktu = SimpleDateFormat("HH:mm", locale)
                complete.timeZone = TimeZone.getTimeZone("GMT+7")
                waktu.timeZone = TimeZone.getTimeZone("GMT+7")
                val mDate = complete.format(date)
                val mTime = waktu.format(date)
                tvSchedule.text = "$mDate (Jam $mTime)"
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }
}