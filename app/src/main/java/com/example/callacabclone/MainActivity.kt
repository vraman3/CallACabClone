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

                redirectActivity()
            }
        }
    }

    fun redirectActivity() {

    }

    fun startButton(view: View) {
        loginSwitch
        //val riderDriverSwitch = findViewById(R.id.) as Switch
        Log.i("Switch Value", loginSwitch.isChecked.toString())

        var userType = "rider"

        if (loginSwitch.isChecked) {
            userType = "driver"
        }

//        if(ParseUser.getCurrentUser() == null) {
//            Log.d("DEBUG", "This is NULLLLLLLLLLLLLL")
//
//        } else {
//            Log.d("DEBUG", ParseUser.getCurrentUser().toString())
//        }

        //Log.d("DEBUG", ParseUser.getCurrentUser().objectId.toString())
        ParseUser.getCurrentUser().put("riderOrDriver", userType)
        ParseUser.getCurrentUser().saveInBackground {
            object : SaveCallback {
                override fun done(e: ParseException?) {
                    Log.d("Info", "Redirecting as $userType")
                    redirectActivity()
                }
            }
        }
    }
}
