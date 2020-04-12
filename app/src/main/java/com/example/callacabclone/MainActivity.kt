package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import com.parse.*
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.example.callacabclone.databinding.ActivityMainBinding
import com.parse.ParseUser




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportActionBar?.hide()

        if(ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn(object: LogInCallback {
                override fun done(user: ParseUser?, e: ParseException?) {

                    if(e == null) {
                        Log.i("INFO", "Anonymous Login Successful")
                    } else {
                        Log.i("INFO", "Anonymous Login Failed")
                    }
                }
                })
        } else {
            if(ParseUser.getCurrentUser().get("riderOrDriver") != null ) {
                Log.i("INFO", "Already logged in as: " + ParseUser.getCurrentUser().get("riderOrDriver"))

                //redirectActivity()
            }
        }
    }

    fun redirectActivity() {
        if (ParseUser.getCurrentUser().get("riderOrDriver") == "rider") {
            //Log.i("Test", "I reached here")
            val intent = Intent(applicationContext, RiderActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(applicationContext, ViewRequestsActivity::class.java)
            startActivity(intent)
        }
    }

    fun startButton(view: View) {

        Log.i("Switch Value", binding.loginSwitch.isChecked.toString())

//        if(ParseUser.getCurrentUser().get("riderOrDriver") != null) {
//            redirectActivity()
//        } else {
        var userType: String// = "rider"

        if (binding.loginSwitch.isChecked) {
            userType = "driver"
        } else {
            userType = "rider"
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType)
        ParseUser.getCurrentUser().saveInBackground(object : SaveCallback {
            override fun done(e: ParseException?) {
                Log.d("Info", "Redirecting as $userType")
                redirectActivity()
            }
        })
//            }
//        }
    }
}
