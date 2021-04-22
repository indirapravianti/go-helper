package com.example.gotukang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.gotukang.fcm.InitializeFCM
import com.example.gotukang.models.UserList
import com.example.gotukang.networks.RetrofitBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var userNames: UserList
    private var fcmToken: InitializeFCM = InitializeFCM()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RetrofitBuilder().getService().getUserList().enqueue(object: Callback<UserList> {
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                Toast.makeText(applicationContext, "Error Network Not Available", Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                progressBar.visibility = View.GONE
                userNames = response.body() ?: UserList()
            }

        })

        loginButton.setOnClickListener {
//            Toast.makeText(this, "Token is ${fcmToken.getFcmToken()}", Toast.LENGTH_SHORT).show()
            Log.d("FCMTOKEN", fcmToken.getFcmToken())

            if(validateLogin() && passwordEditText.text.toString() == "password123") {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", usernameEditText.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Username or password invalid", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateLogin(): Boolean {
        return if (usernameEditText.text.isNullOrEmpty() || passwordEditText.text.isNullOrEmpty()) {
            false
        } else usernameInDatabase()
    }

    private fun usernameInDatabase(): Boolean {
        Log.d("USERNAMEALL", userNames.toString())
        for (user in userNames.list ?: listOf()) {
            if(user?.username?.equals(usernameEditText.text.toString(), ignoreCase = true) == true) {
                return true
            }
        }
        return false
    }


}