package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import androidx.test.espresso.matcher.ViewMatchers.isChecked
import android.R
import android.widget.Switch
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.parse.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.parse.ParseUser




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.callacabclone.R.layout.activity_main)

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
            Log.i("DEBUG", "Rider redirection")
            val intent = Intent(applicationContext, RiderActivity::class.java)
            // DEBUG THIS
            startActivity(intent)
        } else if((ParseUser.getCurrentUser().get("riderOrDriver") == "driver")){
            Log.i("DEBUG","Driver redirection")
            val intent = Intent(applicationContext, ViewRequestsActivity::class.java)
            startActivity(intent)
        }
    }

    fun startButton(view: View) {

        Log.d("Switch Value", loginSwitch.isChecked.toString())

//        if(ParseUser.getCurrentUser().get("riderOrDriver") == null) {
////            redirectActivity()
//        } else
//        if (true) {
        var userType: String// = "rider"

        if (loginSwitch.isChecked) {
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
//        }
    }
}
