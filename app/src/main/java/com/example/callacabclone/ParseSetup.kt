package com.example.callacabclone

import android.app.Application
import android.util.Log
import com.parse.Parse
import com.parse.ParseObject

class ParseSetup: Application() {

    override fun onCreate() {
        super.onCreate()

        //bitnami password 2z4zc49SD0Pt
        //appId: "df510d952d8b4bc49dbef8dcf86380aa51117362",
        //masterKey: "551edc3f932c7e41cd8fbb03f6b9f1379bbb42dc",
        //serverURL: "http://18.219.114.244:80/parse",

        Parse.initialize(Parse.Configuration.Builder (this)
            .applicationId("df510d952d8b4bc49dbef8dcf86380aa51117362")
            .clientKey("551edc3f932c7e41cd8fbb03f6b9f1379bbb42dc")
            .server("http://18.219.114.244:80/parse/")
            .build()
        )

        val nameValueObject = ParseObject("NameValue")
        nameValueObject.put("name", "vish")
        nameValueObject.put("value", "9001")
        nameValueObject.put("etc","random field")

        nameValueObject.saveInBackground {
            Log.d("DEBUG", "Object saved. Id: " + nameValueObject.objectId)
        }
    }
}