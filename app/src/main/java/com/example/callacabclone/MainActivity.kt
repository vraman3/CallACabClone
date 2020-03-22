package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.parse.ParseUser
//import androidx.test.espresso.matcher.ViewMatchers.isChecked
import android.R
import android.widget.Switch
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.parse.ParseException
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.callacabclone.R.layout.activity_main)
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

        if(ParseUser.getCurrentUser() == null) {
            Log.d("DEBUG", "This is NULLLLLLLLLLLLLL")

        } else {
            Log.d("DEBUG", ParseUser.getCurrentUser().toString())
        }

        //Log.d("DEBUG", ParseUser.getCurrentUser().objectId.toString())
        ParseUser.getCurrentUser().put("riderOrDriver", userType)
        ParseUser.getCurrentUser().saveInBackground {
            object : SaveCallback {
                override fun done(e: ParseException?) {
                    Log.d("Info", "Redirecting as $userType")
                }
            }
        }



//        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//
//                redirectActivity();
//            }
//        });

        // Converted Kotlin code for above snipped, given below
//            ParseUser.getCurrentUser().saveInBackground(object : SaveCallback() {
//            fun done(e: ParseException) {
//
//                redirectActivity()
//            }
//        })


    }
}
